package com.project.tgdiscountservice.client;

import com.project.tgdiscountservice.model.CategoryDto;
import com.project.tgdiscountservice.model.Coupon;
import com.project.tgdiscountservice.model.Partner;

import java.util.List;

public interface DiscountAdapter {

    List<CategoryDto> getCategories();

    List<Coupon> getCouponsByPartnerId(Long id);

    List<Partner> getPartners();

}
