package com.example.originalspecialmove.controller.room;

import com.example.originalspecialmove.domain.Room;

import jakarta.persistence.PostUpdate;

public class RoomEntityListener {
    @PostUpdate
    public void postUpdate(Room room) {
        RoomChangeNotifier.notifyChange(room);
    }
}
