package com.poalim.hackaton.service.feign.object;

import lombok.Data;

import java.util.List;

@Data
public class AnalalyzeResponse {

    private  List<BenefitsObject> benefits;
    private String category;
}
