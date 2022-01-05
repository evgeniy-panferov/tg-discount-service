package com.project.tgdiscountservice.client;

import com.project.tgdiscountservice.model.Category;
import com.project.tgdiscountservice.model.Coupon;
import com.project.tgdiscountservice.model.Partner;

import java.util.List;

public interface DiscountAdapter {

    List<Category> getCategories();

    List<Coupon> getCouponsByPartnerId(Long id);

    List<Partner> getPartners();

    List<Partner> getPartnersById(Long id);

}
