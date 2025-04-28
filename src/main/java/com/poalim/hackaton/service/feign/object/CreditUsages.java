package com.poalim.hackaton.service.feign.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditUsages {

    private String merchant;
    private Double amount;
    private String date;

}
