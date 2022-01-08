package com.project.tgdiscountservice.client;

import com.project.tgdiscountservice.model.Category;
import com.project.tgdiscountservice.model.Partner;

import java.util.List;

public interface DiscountAdapter {

    List<Category> getCategories();

    List<Partner> getPartners();

    List<Partner> getPartnersById(Long id);

}
