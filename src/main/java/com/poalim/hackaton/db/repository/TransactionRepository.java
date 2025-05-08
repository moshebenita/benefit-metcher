package com.poalim.hackaton.db.repository;


import com.poalim.hackaton.db.model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<Transaction, String> {
}
