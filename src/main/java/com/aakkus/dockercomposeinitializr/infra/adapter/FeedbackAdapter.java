package com.aakkus.dockercomposeinitializr.infra.adapter;

import com.aakkus.dockercomposeinitializr.domain.FeedbackPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
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
    @Retryable(backoff = @Backoff(value = 3000L))
    public void send(String feedback) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setText(feedback);
        simpleMailMessage.setTo(retrieveMailProperty("to"));
        simpleMailMessage.setSubject(retrieveMailProperty("subject"));

        mailSender.send(simpleMailMessage);

        logger.info("Feedback sent: {}", simpleMailMessage);
    }

    @Recover
    private void feedbackSendRecover(Exception e, String feedback) {
        logger.error("An error occurred while sending feedback: {}, Exception: {}", feedback, e);
    }

    private String retrieveMailProperty(String property) {
        return mailProperties.getProperties().get(property);
    }
}