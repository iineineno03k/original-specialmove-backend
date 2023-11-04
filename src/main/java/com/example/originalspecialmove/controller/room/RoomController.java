package com.example.originalspecialmove.controller.room;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.example.originalspecialmove.domain.Room;
import com.example.originalspecialmove.domain.dto.RoomDto;
import com.example.originalspecialmove.domain.dto.SpecialMoveDeckDto;
import com.example.originalspecialmove.repository.RoomRepository;
import com.example.originalspecialmove.request.RoomRequest;
import com.example.originalspecialmove.service.LineUserService;
import com.example.originalspecialmove.service.SpecialMoveService;

import jakarta.annotation.PostConstruct;

@RestController
@CrossOrigin("*")
public class RoomController {
    @Autowired
    private RoomRepository roomRepo;
    @Autowired
    private LineUserService lineUserService;
    @Autowired
    private SpecialMoveService spService;

    @ResponseBody
    @GetMapping(value = "/rooms/{roomCode}")
    public ResponseEntity<RoomDto> getRoom(@PathVariable String roomCode) throws Exception {
        Optional<Room> roomOptional = roomRepo.findByRoomCode(roomCode);
        if (roomOptional.isPresent()) {
            return ResponseEntity.ok(new RoomDto(roomOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseBody
    @PostMapping(value = "/rooms/battler")
    public void putBattler(@RequestBody RoomRequest request) throws Exception {
        // Pair<String, String> userInfo =
        // lineUserService.getLineUserInfo(request.getIdToken());
        Pair<String, String> userInfo = Pair.of("fuga", "稲田裕次郎");

        Room room = roomRepo.findByRoomCode(request.getRoomCode()).get();
        if (userInfo.getFirst().equals(room.getAUserId()) || userInfo.getFirst().equals(room.getJudgeUserId())) {
            throw new Exception("既に参加しているユーザです");
        }

        if (room.getAUserId() == null) {
            room.setAUserId(userInfo.getFirst());
            room.setAUserName(userInfo.getSecond());
            roomRepo.save(room);
        } else if (room.getBUserId() == null) {
            room.setBUserId(userInfo.getFirst());
            room.setBUserName(userInfo.getSecond());
            roomRepo.save(room);
        } else {
            throw new Exception();
        }
    }

    @ResponseBody
    @PostMapping(value = "/rooms/judger")
    public void putJudger(@RequestBody RoomRequest request) throws Exception {
        // Pair<String, String> userInfo =
        // lineUserService.getLineUserInfo(request.getIdToken());
        Pair<String, String> userInfo = Pair.of("fuga", "稲田裕次郎");

        Room room = roomRepo.findByRoomCode(request.getRoomCode()).get();
        if (userInfo.getFirst().equals(room.getAUserId()) || userInfo.getFirst().equals(room.getBUserId())) {
            throw new Exception("既に参加しているユーザです");
        }

        room.setJudgeUserId(userInfo.getFirst());
        room.setJudgeUserName(userInfo.getSecond());
        roomRepo.save(room);
    }

    @GetMapping("/judge/{roomCode}")
    public ResponseEntity<Void> judge(@PathVariable String roomCode, @RequestParam String result) {
        if (!"A".equals(result) && !"B".equals(result)) {
            return ResponseEntity.badRequest().build(); // 不正な入力を拒否
        }
        RoomChangeNotifier.notifyJudgeResult(roomCode, result);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-deck-localbattle/{roomCode}")
    public ResponseEntity<Map<String, List<SpecialMoveDeckDto>>> getSpecialMoveDecksByRoomCode(
            @PathVariable String roomCode) throws Exception {
        Room room = roomRepo.findByRoomCode(roomCode).get();
        Map<String, List<SpecialMoveDeckDto>> result = new HashMap<>();
        List<SpecialMoveDeckDto> deckA = spService.getSpecialMoveDeck(room.getAUserId());
        List<SpecialMoveDeckDto> deckB = spService.getSpecialMoveDeck(room.getBUserId());
        result.put(room.getAUserName(), deckA);
        result.put(room.getBUserName(), deckB);

        System.out.println("デッキ内容は" + result);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/rooms/stream/{roomCode}")
    public SseEmitter stream(@PathVariable String roomCode) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        RoomChangeNotifier.addEmitter(roomCode, emitter);
        return emitter;
    }

    @PostConstruct
    public void init() {
        Room room = new Room();
        room.setRoomCode("A1717");
        roomRepo.save(room);
    }

}
