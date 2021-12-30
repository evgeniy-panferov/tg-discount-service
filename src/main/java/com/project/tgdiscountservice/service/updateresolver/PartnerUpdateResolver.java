package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.http.TelegramSender;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PartnerUpdateResolver implements TelegramUpdateResolver {

    private final TelegramSender sender;

    @Override
    public void prepareMessage(InnerUpdate update) {
    }
}
