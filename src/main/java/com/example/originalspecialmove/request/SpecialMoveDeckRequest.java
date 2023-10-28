package com.example.originalspecialmove.request;

import com.example.originalspecialmove.domain.dto.SpecialMoveDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpecialMoveDeckRequest {
    private String idToken;
    private SpecialMoveDto sp;
}
