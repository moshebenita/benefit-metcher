package com.poalim.hackaton.service.feign.object;

import lombok.Data;

@Data
public class CreditUsages {

    private String merchant;
    private Double amount;
    private String date;

}
