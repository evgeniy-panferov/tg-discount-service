package com.project.tgdiscountservice.client;

import com.project.tgdiscountservice.client.request.TgRequest;
import com.project.tgdiscountservice.model.Category;
import com.project.tgdiscountservice.model.Coupon;
import com.project.tgdiscountservice.model.Partner;
import com.project.tgdiscountservice.model.dto.CouponDto;

import java.util.List;

public interface DiscountAdapter {

    List<Category> getCategories();

    List<Partner> getPartners();

    List<Partner> getPartnersById(Long id);

    List<Coupon> searchCoupons(TgRequest request);
}
