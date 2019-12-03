package com.example.springdemo.repositories;

import com.example.springdemo.entities.Recommendation;
import org.springframework.data.repository.Repository;

import javax.persistence.criteria.CriteriaBuilder;

public interface RecommendationRepository extends CRUDRepository<Recommendation>, Repository<Recommendation, Integer> {
}
