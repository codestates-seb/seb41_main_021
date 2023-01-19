package com.yata.backend.domain.chat.service;

import com.yata.backend.domain.chat.entity.ChatRoomEntity;
import com.yata.backend.domain.chat.repository.JpaChatRoomRepository;

import javax.transaction.Transactional;

@Transactional
public class ChatRoomServiceImpl implements ChatRoomService {
    private final JpaChatRoomRepository chatRoomRepository;

    public ChatRoomServiceImpl(JpaChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    public ChatRoomEntity createChatRoom(String chatRoomName) {
        return null;
    }
    public ChatRoomEntity getChatRoom(Long chatRoomId) {
        return validateChatRoom(chatRoomId);
    }
    // TODO : 채팅방 목록 조회
    private ChatRoomEntity validateChatRoom(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));
    }

}
