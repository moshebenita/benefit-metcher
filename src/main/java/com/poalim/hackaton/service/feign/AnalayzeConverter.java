package com.poalim.hackaton.service.feign;

import com.poalim.hackaton.db.model.Benefit;
import com.poalim.hackaton.db.model.Transaction;
import com.poalim.hackaton.service.feign.object.AnalalyzeResponse;
import com.poalim.hackaton.service.feign.object.AnalayzeRequest;
import com.poalim.hackaton.service.feign.object.BenefitsObject;
import com.poalim.hackaton.service.feign.object.CreditUsages;
import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class AnalayzeConverter {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public AnalayzeRequest convertAnalayzeRequest(List<Benefit> benefitsData,List<Transaction> transactions){
        List<BenefitsObject> benefitsObjectList = benefitsData.stream().map(b -> convertBenefit(b)).collect(Collectors.toList());
        List<CreditUsages> creditUsagesList = transactions.stream().map(t -> convertTransaction(t)).collect(Collectors.toList());
        AnalayzeRequest analayzeRequest = new AnalayzeRequest(creditUsagesList,benefitsObjectList);
        return analayzeRequest;
    }

    private CreditUsages convertTransaction(Transaction transaction){
        CreditUsages creditUsages = new CreditUsages(transaction.getMerchantName(),transaction.getAmount(),transaction.getCreateDate().format(formatter));
        return creditUsages;
    }
    private BenefitsObject convertBenefit(Benefit b) {
        BenefitsObject benefitsObject = new BenefitsObject(b.getCategory(),b.getDescription());
        return benefitsObject;
    }


//    private

}
