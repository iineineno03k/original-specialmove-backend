package com.example.originalspecialmove.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.originalspecialmove.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomCode(String roomCode);
}
