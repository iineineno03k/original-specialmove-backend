package com.example.originalspecialmove.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.originalspecialmove.domain.SpecialMove;

public interface SpecialMoveRepository extends JpaRepository<SpecialMove, Long> {
    List<SpecialMove> findByUserIdNot(String userId);

    Page<SpecialMove> findAllByOrderByWinCountDesc(Pageable pageable);

    // バトルカウントが5以上で、勝率でソートし、上位10件を取得
    @Query("SELECT sm FROM SpecialMove sm WHERE sm.battleCount >= 5 ORDER BY sm.winRate DESC, sm.battleCount DESC")
    Page<SpecialMove> findTopByWinRateWithMinimumBattles(Pageable pageable);
}
