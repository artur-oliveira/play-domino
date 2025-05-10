package org.playdomino.services.faq;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.playdomino.components.messages.MessagesComponent;
import org.playdomino.models.faq.FAQEnum;
import org.playdomino.models.faq.FAQResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FaqServiceImpl implements FaqService {
    private final List<FAQResponse> faqResponses;

    public FaqServiceImpl(MessagesComponent messagesComponent) {
        this.faqResponses = Arrays
                .stream(FAQEnum.values())
                .map(it -> FAQResponse
                        .builder()
                        .question(messagesComponent.getFaqMessage(it.getQuestion()))
                        .answer(messagesComponent.getFaqMessage(it.getAnswer()))
                        .build())
                .toList();
    }

    @Override
    public List<FAQResponse> getMainFaq() {
        return faqResponses;
    }
}
