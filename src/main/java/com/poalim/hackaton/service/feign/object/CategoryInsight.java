package com.poalim.hackaton.service.feign.object;

import com.poalim.hackaton.rest.object.Benefit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryInsight {
    private String category;
    private List<Benefit> benefits;
}
