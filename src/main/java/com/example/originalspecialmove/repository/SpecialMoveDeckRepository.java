package com.example.originalspecialmove.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.originalspecialmove.domain.SpecialMoveDeck;

public interface SpecialMoveDeckRepository extends JpaRepository<SpecialMoveDeck, Long> {
    List<SpecialMoveDeck> findByLineUserIdAndIsSettingTrue(String userId);

}
