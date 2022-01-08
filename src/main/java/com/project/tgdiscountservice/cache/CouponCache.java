package com.project.tgdiscountservice.cache;

import com.project.tgdiscountservice.model.Coupon;

import java.util.List;
import java.util.Map;

public interface CouponCache extends TelegramRepository<Coupon> {

    Coupon findCoupons(String id);

    void save(Coupon t);

    List<Coupon> findAll();

    void clear();

    void invalidateMessage(String id);

    void saveAll(Map<String, Coupon> categoryById);
}
