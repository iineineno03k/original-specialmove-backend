package com.example.originalspecialmove.domain.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class SpecialMoveDeckDto {
    private Long id;
    private String userId;
    private Long deckId;

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

    public SpecialMoveDeckDto(SpecialMoveDto sp, Long deckId) {
        this.id = sp.getId();
        this.userId = sp.getUserId();
        this.deckId = deckId;
        this.spName = sp.getSpName();
        this.furigana = sp.getFurigana();
        this.heading = sp.getHeading();
        this.description = sp.getDescription();
        this.imageName = sp.getImageName();
        this.registedTime = sp.getRegistedTime();
        this.battleCount = sp.getBattleCount();
        this.winCount = sp.getWinCount();
        this.loseCount = sp.getLoseCount();
    }
}
