package com.poalim.hackaton.rest.object;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Benefit {

    String id;
    String title;
    String description;
    String savings;
    int points;
    String category;
    String image;
    String type;
    String purchaseDate;
    String originalPrice;

}
