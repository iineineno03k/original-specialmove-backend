package com.example.originalspecialmove.controller.room;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.originalspecialmove.domain.Room;
import com.example.originalspecialmove.repository.RoomRepository;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

@LineMessageHandler
@RestController
public class LineBotController {
    @Autowired
    private RoomRepository roomRepo;

    @Autowired
    private LineMessagingClient lineMessagingClient;

    @EventMapping
    public ResponseEntity<Void> handleTextMessage(MessageEvent<TextMessageContent> event) {
        String userMessage = event.getMessage().getText();

        if ("オレ技対戦を申し込む".equals(userMessage)) {
            String roomCode = getRandomRoomCode();
            String liffUrl = "https://liff.line.me/2001116233-Xw8xde2q?roomCode=" + roomCode;

            Room room = new Room();
            room.setRoomCode(roomCode);
            roomRepo.save(room);

            TextMessage replyMessage = new TextMessage("オレ技対戦会場はこちらだ！\n" + liffUrl);
            ReplyMessage reply = new ReplyMessage(event.getReplyToken(), replyMessage);

            lineMessagingClient.replyMessage(reply);

            return ResponseEntity.ok().build();
        }
        return ResponseEntity.ok().build();
    }

    private String getRandomRoomCode() {
        return RandomStringUtils.randomAlphanumeric(5).toUpperCase();
    }
}
