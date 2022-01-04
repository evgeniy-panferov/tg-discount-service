package com.project.tgdiscountservice.client;

import com.project.tgdiscountservice.model.Category;
import com.project.tgdiscountservice.model.dto.CouponDto;
import com.project.tgdiscountservice.model.dto.PartnerDto;

import java.util.List;

public interface DiscountAdapter {

    List<Category> getCategories();

    List<CouponDto> getCouponsByPartnerId(Long id);

    List<PartnerDto> getPartners();

    List<PartnerDto> getPartnersByCategoryId(Long id);

}
