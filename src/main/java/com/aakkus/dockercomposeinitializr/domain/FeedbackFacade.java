package com.aakkus.dockercomposeinitializr.domain;

import org.springframework.stereotype.Service;

@Service
public class FeedbackFacade {

    private final FeedbackPort feedbackPort;

    public FeedbackFacade(FeedbackPort feedbackPort) {
        this.feedbackPort = feedbackPort;
    }

    public void sendFeedback(String feedback) {
        feedbackPort.send(feedback);
    }
}