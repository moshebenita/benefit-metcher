package com.poalim.hackaton.service.feign;

import com.poalim.hackaton.service.feign.object.AnalalyzeResponse;
import com.poalim.hackaton.service.feign.object.AnalayzeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "analyzeClient", url = "http://localhost:5000")
public interface AnalyzeClient {

    @PostMapping(value = "/analyze", consumes = "application/json")
    AnalalyzeResponse analyze(@RequestBody AnalayzeRequest request);


}
