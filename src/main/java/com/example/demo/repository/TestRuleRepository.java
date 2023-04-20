package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.TestRule;

public interface TestRuleRepository extends JpaRepository<TestRule, Integer> {

}
