package com.poalim.hackaton.rest;

import com.poalim.hackaton.db.model.Benefit;
import com.poalim.hackaton.db.repository.BenefitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("dao/benefit")
@RequiredArgsConstructor
public class BenefitsApi {

    private final BenefitRepository benefitRepository;
    @PostMapping("/addNew")
    public void addNewBenefit(@RequestBody Benefit benefit){
        benefitRepository.save(benefit);
    }


}
