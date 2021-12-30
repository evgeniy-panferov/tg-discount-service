package com.project.tgdiscountservice.service.updateresolver;

import com.project.tgdiscountservice.http.TelegramSender;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponUpdateResolver implements TelegramUpdateResolver {

    private final TelegramSender sender;

    @Override
    public void prepareMessage(InnerUpdate update) {

    }
}
