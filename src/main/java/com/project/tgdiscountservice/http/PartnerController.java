package com.project.tgdiscountservice.http;

import com.project.tgdiscountservice.cache.PartnerCacheImpl;
import com.project.tgdiscountservice.model.Partner;
import com.project.tgdiscountservice.model.dto.PartnerDto;
import com.project.tgdiscountservice.util.PartnerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("partners")
public class PartnerController {

    private final PartnerCacheImpl couponCache;

    @PostMapping
    public ResponseEntity<?> partnerHandler(@RequestBody List<PartnerDto> partnerDtos) {
        log.info("PartnerController partnerHandler - {}", partnerDtos);
        List<Partner> partners = PartnerUtil.fromDtos(partnerDtos);
        Map<String, Partner> partnerById = partners
                .stream()
                .collect(Collectors.toMap(partner -> partner.getId().toString(), Function.identity()));

        couponCache.saveAll(partnerById);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
