package com.project.tgdiscountservice.service;

import com.project.tgdiscountservice.client.DiscountClientAdapterImpl;
import com.project.tgdiscountservice.model.Partner;
import com.project.tgdiscountservice.model.inner.InnerUpdate;
import com.project.tgdiscountservice.service.sender.MessageSender;
import com.project.tgdiscountservice.service.updateresolver.CouponUpdateResolver;
import com.project.tgdiscountservice.util.UpdateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CouponService {

    private final MessageSender sender;
    private final DiscountClientAdapterImpl clientAdapter;
    private final KeyboardPageGeneration<Partner> pageGeneration;
    private final CouponUpdateResolver couponUpdateResolver;
    private final String COMMAND = "/coupons";


    public void getCoupons(long chatId, long categoryId) {
        InnerUpdate innerUpdate = UpdateUtil.dtoUpdate(chatId, COMMAND);
        couponUpdateResolver.prepareMessage(innerUpdate, categoryId);
    }
}
