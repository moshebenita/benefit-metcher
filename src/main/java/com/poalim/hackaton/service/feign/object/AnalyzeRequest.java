package com.poalim.hackaton.service.feign.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.poalim.hackaton.db.model.Benefit;
import com.poalim.hackaton.db.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnalyzeRequest {


    @JsonProperty("credit_usages")
    private List<Transaction> creditUsages;

    @JsonProperty("benefits")
    private List<Benefit> benefitsObjects;

}
