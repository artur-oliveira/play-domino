package org.playdomino.services.faq;

import org.playdomino.models.faq.FAQResponse;

import java.util.List;

public interface FaqService {
    List<FAQResponse> getMainFaq();
}
