package com.jitendra.chatApp.repo;

import com.jitendra.chatApp.entity.Room;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoomRepo extends MongoRepository<Room,String> {

    Room findByRoomId(String roomId);
}
