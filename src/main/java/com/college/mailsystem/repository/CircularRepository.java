package com.college.mailsystem.repository;

import com.college.mailsystem.model.Circular;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CircularRepository extends MongoRepository<Circular, String> {
}
