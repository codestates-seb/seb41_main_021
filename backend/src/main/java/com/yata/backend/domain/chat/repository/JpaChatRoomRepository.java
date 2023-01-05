package com.yata.backend.domain.chat.repository;

import com.yata.backend.domain.chat.entity.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRoomRepository extends JpaRepository<ChatRoomEntity , Long> , ChatRoomRepository {
}
