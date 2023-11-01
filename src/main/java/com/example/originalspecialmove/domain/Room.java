package com.example.originalspecialmove.domain;

import com.example.originalspecialmove.controller.room.RoomEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@EntityListeners(RoomEntityListener.class)
@Entity
@Table(name = "room")
@Data
@NoArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roomCode; // 5桁のランダム文字列
    private String aUserId;
    private String aUserName;
    private String bUserId;
    private String bUserName;
    private String judgeUserId;
    private String judgeUserName;
}
