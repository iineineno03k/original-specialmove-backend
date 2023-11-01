package com.example.originalspecialmove.controller.room;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.originalspecialmove.domain.Room;
import com.example.originalspecialmove.domain.dto.RoomDto;

public class RoomChangeNotifier {
    private static final Map<String, List<SseEmitter>> emitters = new ConcurrentHashMap<>();

    public static void addEmitter(String roomCode, SseEmitter emitter) {
        emitters
                .computeIfAbsent(roomCode, k -> new CopyOnWriteArrayList<>())
                .add(emitter);

        emitter.onCompletion(() -> {
            List<SseEmitter> emittersList = emitters.get(roomCode);
            if (emittersList != null) {
                emittersList.remove(emitter);
            }
        });

        emitter.onTimeout(() -> {
            emitter.complete();
            List<SseEmitter> emittersList = emitters.get(roomCode);
            if (emittersList != null) {
                emittersList.remove(emitter);
            }
        });
    }

    public static void notifyChange(Room room) {
        List<SseEmitter> emittersList = emitters.get(room.getRoomCode());
        RoomDto roomDto = new RoomDto(room);
        if (emittersList != null) {
            for (SseEmitter emitter : emittersList) {
                try {
                    emitter.send(SseEmitter.event().name("roomChange").data(roomDto));
                } catch (IOException e) {
                    e.printStackTrace();
                    // 送信に失敗した場合、リストからエミッターを削除することも考えられます
                    emittersList.remove(emitter);
                }
            }
        }
    }

    public static void notifyJudgeResult(String roomCode, String result) {
        List<SseEmitter> emittersList = emitters.get(roomCode);
        if (emittersList != null) {
            for (SseEmitter emitter : emittersList) {
                try {
                    emitter.send(SseEmitter.event().name("judgeResult").data(result));
                } catch (IOException e) {
                    e.printStackTrace();
                    emittersList.remove(emitter);
                }
            }
        }
    }
}
