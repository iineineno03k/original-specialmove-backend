package com.example.originalspecialmove.domain.dto;

import java.time.LocalDateTime;

import com.example.originalspecialmove.domain.SpecialMove;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SpecialMoveDto {
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

    public SpecialMoveDto(SpecialMove sp) {
        this.id = sp.getId();
        this.userId = sp.getUserId();
        this.spName = sp.getSpName();
        this.furigana = sp.getFurigana();
        this.heading = sp.getHeading();
        this.description = sp.getDescription();
        this.imageName = sp.getImagePath();
        this.registedTime = sp.getRegistedTime();
        this.battleCount = sp.getBattleCount();
        this.winCount = sp.getWinCount();
        this.loseCount = sp.getLoseCount();
    }
}
