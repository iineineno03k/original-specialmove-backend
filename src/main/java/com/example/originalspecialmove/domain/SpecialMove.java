package com.example.originalspecialmove.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.Formula;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "special_move")
public class SpecialMove {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;

    private String spName;
    private String furigana;
    private String heading;
    private String description;
    private String imageName;
    private LocalDateTime registedTime;
    // 戦績
    private int battleCount;
    private int winCount;
    private int loseCount;
    @Formula("CASE WHEN battleCount > 0 THEN (winCount * 1.0 / battleCount) ELSE 0 END")
    private double winRate;

    public String getImagePath() {
        return "https://pub-5c00d9cd767343259424b03f8a52941a.r2.dev/originalmove/" + imageName;
    }

    public SpecialMove winResult() {
        this.battleCount += 1;
        this.winCount += 1;
        return this;
    }

    public SpecialMove loseResult() {
        this.battleCount += 1;
        this.loseCount += 1;
        return this;
    }
}
