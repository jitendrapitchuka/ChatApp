package com.jitendra.chatApp.controller;

import com.jitendra.chatApp.entity.Message;
import com.jitendra.chatApp.entity.Room;
import com.jitendra.chatApp.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @PostMapping
    public ResponseEntity<?> createRoom(@RequestBody  String roomId){
         String s=roomService.createRoom(roomId);

         if(s.equals("room Already Exists"))
         {
             return ResponseEntity.badRequest().body(s);
         }
         return ResponseEntity.status(HttpStatus.CREATED).body(s);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> joinRoom(@PathVariable String roomId){
        Room room=roomService.getRoom(roomId);

        if(room==null)
            return ResponseEntity.badRequest().body("Room not found");

        return ResponseEntity.ok().body(room);
    }

    @GetMapping("/{roomId}/messages")
    public ResponseEntity<?> getMessages(@PathVariable String roomId,
                                         @RequestParam(value = "page",defaultValue = "0",required = false)int page,
                                         @RequestParam(value = "size",defaultValue = "20",required = false)int size){
        List<Message>  paginatedMessages=roomService.getMessages(roomId,page,size);

        if(paginatedMessages.isEmpty())
            return ResponseEntity.badRequest().body("Send wrong Info");

        return ResponseEntity.ok().body(paginatedMessages);
    }



}
