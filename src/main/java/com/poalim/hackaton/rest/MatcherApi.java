package com.poalim.hackaton.rest;

import com.poalim.hackaton.process.ProcessApiRequest;
import com.poalim.hackaton.rest.object.Benefit;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
@CrossOrigin
@RequiredArgsConstructor
public class MatcherApi {

    private final ProcessApiRequest processApiRequest;

    @GetMapping("/missedOpportunities/{customerId}")
    public List<Benefit> missedOpportunities(@PathVariable String customerId){
        return processApiRequest.getMissedOpportunities(customerId);
    }

    @GetMapping("/matchBenefits/{customerId}")
    public List<Benefit> matchBenefits(@PathVariable String customerId){
        return processApiRequest.getMatchBenefits(customerId);
    }
}
