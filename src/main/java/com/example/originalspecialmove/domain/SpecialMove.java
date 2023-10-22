package com.example.originalspecialmove.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class SpecialMove {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;

    private String name;
    private String furigana;
    private String heading;
    private String description;
    private String imageName;
    // 戦績
    private int battleCount;
    private int winCount;
    private int loseCount;

    public String getImagePath() {
        return "https://pub-5c00d9cd767343259424b03f8a52941a.r2.dev/originalmove/" + imageName;
    }
}
