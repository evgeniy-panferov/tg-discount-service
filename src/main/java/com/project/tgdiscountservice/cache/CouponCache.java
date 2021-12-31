package com.project.tgdiscountservice.cache;

import com.project.tgdiscountservice.model.Coupon;

public interface CouponCache extends TelegramRepository<Coupon> {

    Coupon find(String id);

    void save(Coupon t);

    void clear();

    void invalidateMessage(String id);
}
