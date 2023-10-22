package com.example.originalspecialmove.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Entity
@Table(name = "special_move_gallary")
public class SpecialMoveGallary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String lineUserId;
    private String authorLineUserId;
    @OneToOne
    private SpecialMove specialMove;
}
