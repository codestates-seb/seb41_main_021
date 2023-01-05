package com.yata.backend.domain.chat.repository;

import com.yata.backend.domain.chat.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaChatRepository extends JpaRepository<ChatEntity , Long>,ChatRepository {

}
