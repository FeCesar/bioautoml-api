package com.bioautoml.domain.email.service;

import com.bioautoml.domain.email.dto.EmailDTO;
import com.bioautoml.domain.email.dto.EmailResponseDTO;
import com.bioautoml.domain.email.enums.ContentType;
import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.result.model.ResultModel;
import com.bioautoml.domain.result.service.ResultService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class EmailService {

    @Value("${application.sendgrid.apikey}")
    private String sendGridKey;

    @Value("${application.sendgrid.from.email}")
    private String email;

    @Autowired
    private ResultService resultService;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendEmail(String processId) {
        ResultModel result = this.resultService.getBy(Long.valueOf(processId)).get();
        ProcessDTO processDTO = result.getProcessModel().toDTO();

        EmailDTO emailDTO = this.createEmail(result.getLink(), processDTO);

        Email from = new Email(emailDTO.getSenderEmail());
        Email to = new Email(emailDTO.getReceiverEmail());
        Content content = new Content(emailDTO.getContentType().getType(), emailDTO.getContent());
        Mail mail = new Mail(from, emailDTO.getSubject(), to, content);

        SendGrid sendGrid = new SendGrid(sendGridKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sendGrid.api(request);

            logger.info("send finished email from process={} with details={} and returns response={}", processDTO.getId(), emailDTO, new EmailResponseDTO(response));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private EmailDTO createEmail(String resultLink, ProcessDTO processDTO) {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setReceiverEmail(processDTO.getEmail());
        emailDTO.setSenderEmail(this.email);
        emailDTO.setSubject("Finished the " + processDTO.getReferenceName() + " process - Bioautoml");
        emailDTO.setContentType(ContentType.TEXT_PLAIN);
        emailDTO.setContent("Wow! Your bioautoml process was finished. Your download link is avaliable on " + resultLink);

        return emailDTO;
    }
}
