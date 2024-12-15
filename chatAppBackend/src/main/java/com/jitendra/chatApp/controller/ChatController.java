package com.jitendra.chatApp.controller;

import com.jitendra.chatApp.entity.Message;
import com.jitendra.chatApp.payload.MessageRequest;
import com.jitendra.chatApp.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    @Autowired
    private RoomService roomService;

    @MessageMapping("/sendMessage/{roomId}") // /app/sendMessage/{roomId}
    @SendTo("/topic/room/{roomId}") //a broadcast destination for multiple clients
    public Message sendMessage(@DestinationVariable String roomId, @RequestBody MessageRequest request){
            return roomService.sendMessage(roomId,request);
    }
}
