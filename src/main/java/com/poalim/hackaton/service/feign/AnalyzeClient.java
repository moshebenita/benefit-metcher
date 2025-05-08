package com.poalim.hackaton.service.feign;

import com.poalim.hackaton.service.feign.object.AnalyzeRequest;
import com.poalim.hackaton.service.feign.object.AnalyzeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "analyzeClient", url = "http://localhost:5000")
public interface AnalyzeClient {

    @PostMapping(value = "/analyze", consumes = "application/json")
    AnalyzeResponse analyze(@RequestBody AnalyzeRequest request);
}
