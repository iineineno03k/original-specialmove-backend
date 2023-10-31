package com.example.originalspecialmove.response;

import java.util.List;

import com.example.originalspecialmove.domain.dto.SpecialMoveDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BattleResponse {
    private List<SpecialMoveDto> battleList;
    private List<SpecialMoveDto> myGallary;
}
