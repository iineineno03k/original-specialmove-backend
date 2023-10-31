package com.example.originalspecialmove.domain.dto;

import java.time.LocalDateTime;

import com.example.originalspecialmove.domain.CheckedSp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CheckedSpDto {
    private Long id;
    private String userId;
    private Long spId;
    private LocalDateTime checkedTime;

    public CheckedSpDto(CheckedSp checkedSp) {
        this.id = checkedSp.getId();
        this.userId = checkedSp.getUserId();
        this.spId = checkedSp.getSpId();
        this.checkedTime = checkedSp.getCheckedTime();
    }
}
