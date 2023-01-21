package com.bioautoml.domain.result.service;

import com.bioautoml.domain.process.repository.ProcessRepository;
import com.bioautoml.domain.result.model.ResultModel;
import com.bioautoml.domain.result.repository.ResultRepository;
import com.bioautoml.domain.result.vo.ResultVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ResultService {

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private ProcessRepository processRepository;

    private static final Logger logger = LoggerFactory.getLogger(ResultService.class);

    @Transactional
    public void save(ResultVO resultVO) {
        this.processRepository.findById(resultVO.getProcessId())
                .ifPresent(processModel -> {
                    ResultModel resultModel = new ResultModel();
                    resultModel.setProcessModel(processModel);
                    resultModel.setLink(resultVO.getLink());

                    this.resultRepository.save(resultModel);
                });
    }

}
