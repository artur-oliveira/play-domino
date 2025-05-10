package org.playdomino.controllers;

import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.faq.FAQResponse;
import org.playdomino.models.generic.ListReponse;
import org.playdomino.services.faq.FaqService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/faq")
@RequiredArgsConstructor
public class FAQController {
    private final FaqService faqService;

    @GetMapping
    public ListReponse<FAQResponse> findAll() {
        return new ListReponse<>(faqService.getMainFaq());
    }
}
