package com.example.originalspecialmove.domain.dto;

import com.example.originalspecialmove.domain.Room;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class RoomDto {
    private String roomCode;
    private String aUserName;
    private String bUserName;
    private String judgeUserName;

    public RoomDto(Room room) {
        this.roomCode = room.getRoomCode();
        this.aUserName = room.getAUserName();
        this.bUserName = room.getBUserName();
        this.judgeUserName = room.getJudgeUserName();
    }
}
