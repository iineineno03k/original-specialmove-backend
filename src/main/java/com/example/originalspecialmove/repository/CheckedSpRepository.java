package com.example.originalspecialmove.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.originalspecialmove.domain.CheckedSp;

public interface CheckedSpRepository extends JpaRepository<CheckedSp, Long> {
    List<CheckedSp> findByUserId(String userId);
}
