package com.poalim.hackaton.db.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("transaction")
@Data
public class Transaction {

    @Id
    private String id;

    private Double amount;
    private String merchantId;
    private String merchantName;

    @CreatedDate
    private LocalDateTime createDate;
}
