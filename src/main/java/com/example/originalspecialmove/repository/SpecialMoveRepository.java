package com.example.originalspecialmove.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.originalspecialmove.domain.SpecialMove;

public interface SpecialMoveRepository extends JpaRepository<SpecialMove, Long> {
    List<SpecialMove> findByUserId(String userId);
}
