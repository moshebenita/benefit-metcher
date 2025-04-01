package com.poalim.hackaton.db.repository;


import com.poalim.hackaton.db.model.Benefit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface  BenefitRepository extends MongoRepository<Benefit, String> {
}
