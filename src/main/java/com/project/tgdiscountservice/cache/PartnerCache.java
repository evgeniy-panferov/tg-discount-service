package com.project.tgdiscountservice.cache;

import com.project.tgdiscountservice.model.Coupon;
import com.project.tgdiscountservice.model.Partner;

import java.util.List;
import java.util.Map;

public interface PartnerCache extends TelegramRepository<Partner> {

    List<Coupon> findCoupons(String id);

    void save(Partner t);

    void clear();

    void invalidatePartner(String id);

    List<Partner> findAll();

    void saveAll(Map<String, Partner> partnerById);
}
