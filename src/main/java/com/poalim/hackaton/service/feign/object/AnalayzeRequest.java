package com.poalim.hackaton.service.feign.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalayzeRequest {


    @JsonProperty("credit_usages")
    private List<CreditUsages> creditUsages;

    @JsonProperty("benefits")
    private List<BenefitsObject> benefitsObjects;

}
