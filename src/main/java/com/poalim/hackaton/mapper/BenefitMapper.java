package com.poalim.hackaton.mapper;

import com.poalim.hackaton.rest.object.Benefit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BenefitMapper {

    List<Benefit> toBenefitList(List<com.poalim.hackaton.db.model.Benefit> benefitListModel);

    @Mapping(target = "savings", expression = "java(String.valueOf(entity.getAmount()))")
    @Mapping(target = "points", source = "score")
    @Mapping(target = "type", constant = "benefit")
    @Mapping(target = "purchaseDate", ignore = true)
    @Mapping(target = "originalPrice", expression = "java(String.valueOf(entity.getAmount()))")
    Benefit toDto(com.poalim.hackaton.db.model.Benefit entity);

    @Mapping(target = "amount", expression = "java(parseAmount(dto.getSavings()))")
    @Mapping(target = "score", source = "points")
    @Mapping(target = "merchantId", ignore = true)
    com.poalim.hackaton.db.model.Benefit toEntity(Benefit dto);

    // Helper method for parsing
    default Double parseAmount(String savings) {
        try {
            return savings != null ? Double.parseDouble(savings) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
