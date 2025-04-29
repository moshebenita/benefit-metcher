package com.poalim.hackaton.process;

import com.poalim.hackaton.rest.object.Benefit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class BenefitMapper {
    public static Benefit map(com.poalim.hackaton.db.model.Benefit source) {
        return Benefit.builder()
                .id(source.getId())
                .title(source.getTitle())
                .description(source.getDescription())
                .savings(source.getAmount() != null ? source.getAmount() + "₪" : "0₪")
                .points(source.getScore())
                .category(source.getCategory())
                .image(source.getImage())
                .type("benefit") // ערך קבוע או דינמי – לפי הצורך שלך
                .purchaseDate(LocalDate.now().format(DateTimeFormatter.ISO_DATE)) // או null אם לא קיים
                .originalPrice(source.getAmount() != null ? String.valueOf(source.getAmount()) : "0")
                .build();
    }

    public static List<Benefit> mapAll(List<com.poalim.hackaton.db.model.Benefit> sourceList) {
        return sourceList.stream()
                .map(BenefitMapper::map)
                .collect(Collectors.toList());
    }
}
