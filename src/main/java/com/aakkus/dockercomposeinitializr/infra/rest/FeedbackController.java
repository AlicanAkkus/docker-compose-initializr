package com.aakkus.dockercomposeinitializr.infra.rest;

import com.aakkus.dockercomposeinitializr.domain.FeedbackFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/feedback")
public class FeedbackController {

    private final FeedbackFacade feedbackFacade;

    public FeedbackController(FeedbackFacade feedbackFacade) {
        this.feedbackFacade = feedbackFacade;
    }

    @PostMapping
    public ResponseEntity sendFeedback(@RequestBody String feedback) {
        feedbackFacade.sendFeedback(feedback);
        return ResponseEntity.ok().build();
    }
}