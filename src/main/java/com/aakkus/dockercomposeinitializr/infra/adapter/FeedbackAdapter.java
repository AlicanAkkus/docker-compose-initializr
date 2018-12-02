package com.aakkus.dockercomposeinitializr.infra.adapter;

import com.aakkus.dockercomposeinitializr.domain.FeedbackPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class FeedbackAdapter implements FeedbackPort {

    private final static Logger logger = LoggerFactory.getLogger(FeedbackAdapter.class);

    private final MailSender mailSender;
    private final MailProperties mailProperties;

    public FeedbackAdapter(MailSender mailSender, MailProperties mailProperties) {
        this.mailSender = mailSender;
        this.mailProperties = mailProperties;
    }

    @Async
    @Override
    public void send(String feedback) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setText(feedback);
        simpleMailMessage.setTo(retrieveMailProperty("to"));
        simpleMailMessage.setSubject(retrieveMailProperty("subject"));

        mailSender.send(simpleMailMessage);

        logger.info("Feedback sent: {}", simpleMailMessage);
    }

    private String retrieveMailProperty(String property) {
        return mailProperties.getProperties().get(property);
    }
}