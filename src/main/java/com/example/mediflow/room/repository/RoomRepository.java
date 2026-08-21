package com.example.mediflow.room.repository;

import com.example.mediflow.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID> {
    boolean existsByNameIgnoreCase(String name);
}
