package com.poalim.hackaton.service.feign.object;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AnalayzeRequest {


    @JsonProperty("credit_usages")
    private List<CreditUsages> creditUsages;

    @JsonProperty("benefits")
    private List<BenefitsObject> benefitsObjects;

}
