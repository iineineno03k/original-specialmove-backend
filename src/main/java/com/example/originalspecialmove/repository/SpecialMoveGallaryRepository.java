package com.example.originalspecialmove.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.originalspecialmove.domain.SpecialMoveGallary;

public interface SpecialMoveGallaryRepository extends JpaRepository<SpecialMoveGallary, Long> {
    List<SpecialMoveGallary> findByLineUserId(String lineUserId);
}
