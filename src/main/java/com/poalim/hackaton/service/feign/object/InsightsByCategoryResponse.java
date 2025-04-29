package com.poalim.hackaton.service.feign.object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsightsByCategoryResponse {
    private List<CategoryInsight> insightsByCategory;
}
