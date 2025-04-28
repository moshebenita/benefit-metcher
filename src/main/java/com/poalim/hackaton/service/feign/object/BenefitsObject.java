package com.poalim.hackaton.service.feign.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BenefitsObject {

    private String business;
    private String description;
}
