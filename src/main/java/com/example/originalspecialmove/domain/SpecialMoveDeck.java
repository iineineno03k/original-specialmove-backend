package com.example.originalspecialmove.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
@Table(name = "special_move_deck")
public class SpecialMoveDeck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "line_user_id")
    private String lineUserId;
    @Column(name = "special_move_id")
    private Long specialMoveId;
    @Column(name = "setting_time")
    private LocalDateTime settingTime;
    @Column(name = "is_setting")
    private boolean isSetting;

}
