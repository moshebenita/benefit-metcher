package com.poalim.hackaton.db.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("benefits")
@Data
public class Benefit {

    @Id
    private String id;
    private String title;
    private String merchantId;
    private String description;
    private Double amount;
    private int score;
    private String category;
    private String image;

}
