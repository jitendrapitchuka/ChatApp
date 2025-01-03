package com.jitendra.chatApp.services;

import com.jitendra.chatApp.entity.Message;
import com.jitendra.chatApp.entity.Room;
import com.jitendra.chatApp.payload.MessageRequest;
import com.jitendra.chatApp.repo.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepo roomRepo;

    public String createRoom(String roomId){
        if(roomRepo.findByRoomId(roomId)!=null)
        {
            return "room Already Exists";
        }
        Room room =new Room();
        room.setRoomId(roomId);
        Room savedRoom= roomRepo.save(room);
        return "room created with "+roomId;
    }

    public Room getRoom(String roomId)
    {
        Room room=roomRepo.findByRoomId(roomId);

        if(room==null)
            return null;

        return room;
    }

    public List<Message> getMessages(String roomId,int page,int size){

        Room room=roomRepo.findByRoomId(roomId);

        if(room==null)
            return null;

        List<Message> messages=room.getMessages();
        int start=Math.max(0, messages.size()-(page+1)*size);
        int end=Math.min(messages.size(),start+size);
        List<Message>paginatedMessages=messages.subList(start,end);



        return paginatedMessages;

    }

    public Message sendMessage(String roomId, MessageRequest request){

        Room room=roomRepo.findByRoomId(roomId);
        Message message=new Message();
        message.setContent(request.getContent());
        message.setSender(request.getSender());
        message.setTimeStamp(LocalDateTime.now());

        if(room!=null){
            room.getMessages().add(message);
            roomRepo.save(room);
        }
        else {
            throw  new RuntimeException("Room Not found");
        }

        return message;
    }
}
