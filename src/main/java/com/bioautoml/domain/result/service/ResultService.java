package com.bioautoml.domain.result.service;

import com.amazonaws.SdkClientException;
import com.bioautoml.domain.email.service.EmailService;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.repository.ProcessRepository;
import com.bioautoml.domain.result.dto.ResultTransferDTO;
import com.bioautoml.domain.result.model.ResultModel;
import com.bioautoml.domain.result.repository.ResultRepository;
import com.bioautoml.domain.result.vo.ResultVO;
import com.bioautoml.storage.providers.AWSProviderService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private AWSProviderService storageService;

    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(ResultService.class);
    private final Gson gson = new Gson();

    @Transactional
    public void save(ResultVO resultVO) {
        this.processRepository.findById(resultVO.getProcessId())
            .ifPresent(processModel -> {
                ResultModel resultModel = new ResultModel();
                resultModel.setProcessModel(processModel);
                resultModel.setLink(resultVO.getLink());
                resultModel.setReferenceDate(resultVO.getReferenceDate());
                this.resultRepository.save(resultModel);
            });
    }

    public void decode(String resultMessage) {
        try{
            String resultJson = new String(Base64.getDecoder().decode(resultMessage));
            ResultTransferDTO resultTransferDTO = this.gson.fromJson(resultJson, ResultTransferDTO.class);
            URL s3FileURL = this.storageService.generateFileURL(resultTransferDTO.getProcessId());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime referenceDate = LocalDateTime.parse(resultTransferDTO.getReferenceDate(), formatter);

            this.save(
                ResultVO.builder()
                    .processId(UUID.fromString(resultTransferDTO.getProcessId()))
                    .link(s3FileURL.toString())
                    .referenceDate(referenceDate)
                    .build()
            );

            ProcessModel processModel = processRepository.findById(
                UUID.fromString(resultTransferDTO.getProcessId())).get();
            this.emailService.sendEmail(s3FileURL.toString(), processModel.toDTO());
        } catch (JsonSyntaxException e) {
            logger.error("error can not deseralizable={}", resultMessage);
        } catch (SdkClientException e) {
            logger.error("error in generate result url={}", e.getMessage());
        } catch (Exception e) {
            logger.error("unknown error={}", e.getMessage());
        }
    }

    public List<ResultModel> getAllBy(UUID userID) {
        return this.resultRepository.findAllByUserId(userID);
    }

    public Optional<ResultModel> getBy(UUID processId) {
        return this.resultRepository.findByProcess(processId);
    }

}
