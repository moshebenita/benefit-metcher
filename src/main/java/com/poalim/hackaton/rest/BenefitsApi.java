package com.poalim.hackaton.rest;

import com.poalim.hackaton.process.ProcessApiRequest;
import com.poalim.hackaton.rest.object.Benefit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class BenefitsApi {

    @Autowired
    private ProcessApiRequest processApiRequest;


    @GetMapping("/missedOpportunities/{customerId}")
    public List<Benefit> missedOpportunities(String customerId){
        return processApiRequest.getMissedOpportunities(customerId);
    }

    @GetMapping("/matchBenefits/{customerId}")
    public List<Benefit> matchBenefits(String customerId){
        return processApiRequest.getMatchBenefits(customerId);
    }
}
