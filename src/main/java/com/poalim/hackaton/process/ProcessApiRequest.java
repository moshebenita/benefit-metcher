package com.poalim.hackaton.process;

import com.poalim.hackaton.db.model.Transaction;
import com.poalim.hackaton.db.repository.BenefitRepository;
import com.poalim.hackaton.db.repository.TransactionRepository;
import com.poalim.hackaton.mapper.BenefitMapper;
import com.poalim.hackaton.rest.object.Benefit;
import com.poalim.hackaton.service.feign.AnalyzeClient;
import com.poalim.hackaton.service.feign.object.AnalyzeRequest;
import com.poalim.hackaton.service.feign.object.AnalyzeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProcessApiRequest {

    private final BenefitRepository benefitRepository;
    private final TransactionRepository transactionRepository;
    private final AnalyzeClient analyzeClient;
    private final BenefitMapper benefitMapper;

    @Cacheable(value = "missedOpportunities", key = "#customerId")
    public List<Benefit> getMissedOpportunities(String customerId){
        List<com.poalim.hackaton.db.model.Benefit> benefitsData = benefitRepository.findAll();
        List<Transaction> transactions = transactionRepository.findAll();

        AnalyzeRequest analyze = new AnalyzeRequest(transactions,benefitsData);

        AnalyzeResponse response = analyzeClient.analyze(analyze);

        List<com.poalim.hackaton.db.model.Benefit> missedBenefits = benefitsData.stream()
                .filter(benefit -> response.getBenefitIds().contains(benefit.getId()))
                .toList();

        return benefitMapper.toBenefitList(missedBenefits);
//        List benefits = new ArrayList();
//        benefits.add( new Benefit("m1"
//                ,"Flight Discounts","You could have saved on your flight to London. Next time use our app!"
//        ,"$75",50,"Travel","/lovable-uploads/8495be99-264e-433e-8d24-f478b4834528.png",
//                "missed", "2023-12-10","$450"));
//        benefits.add( new Benefit("m2"
//                ,"Restaurant Deal","You paid full price at The Italian Kitchen. We offer 15% cashback."
//                ,"$23",30,"Dining","https://images.unsplash.com/photo-1414235077428-338989a2e8c0?q=80&w=500",
//                "missed", "2023-12-10","$155"));
//
//        benefits.add( new Benefit("m3"
//                ,"Hotel Booking","Your stay at Grand Hotel could have been cheaper with our rewards."
//                ,"$120",30,"Accommodation","https://images.unsplash.com/photo-1566073771259-6a8506099945?q=80&w=500",
//                "missed", "2024-12-10","$800"));
//        return benefits;
    }

    @Cacheable(value = "matchBenefits", key = "#customerId")
    public List<Benefit> getMatchBenefits(String customerId){
        List<com.poalim.hackaton.db.model.Benefit> benefitsData = benefitRepository.findAll();
        List<Transaction> transactions = transactionRepository.findAll();

        Set<String> merchantIdsInTransactions = transactions.stream()
                .map(Transaction::getMerchantId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<com.poalim.hackaton.db.model.Benefit> matchedBenefits = benefitsData.stream()
                .filter(benefit -> merchantIdsInTransactions.contains(benefit.getMerchantId()))
                .collect(Collectors.toList());

        return benefitMapper.toBenefitList(matchedBenefits);

//        List benefits = new ArrayList();
//        benefits.add( new Benefit("m1"
//                ,"Flight Discounts","You could have saved on your flight to London. Next time use our app!"
//                ,"$75",50,"Travel","/lovable-uploads/8495be99-264e-433e-8d24-f478b4834528.png",
//                "missed", "2023-12-10","$450"));
//        benefits.add( new Benefit("m2"
//                ,"Restaurant Deal","You paid full price at The Italian Kitchen. We offer 15% cashback."
//                ,"$23",30,"Dining","https://images.unsplash.com/photo-1414235077428-338989a2e8c0?q=80&w=500",
//                "missed", "2023-12-10","$155"));
//
//        benefits.add( new Benefit("m3"
//                ,"Hotel Booking","Your stay at Grand Hotel could have been cheaper with our rewards."
//                ,"$120",30,"Accommodation","https://images.unsplash.com/photo-1566073771259-6a8506099945?q=80&w=500",
//                "missed", "2024-12-10","$800"));
//        return benefits;
    }
}
