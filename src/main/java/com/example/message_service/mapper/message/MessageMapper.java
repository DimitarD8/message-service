package com.example.message_service.mapper.message;

import com.example.message_service.dto.message.MessageDto;
import com.example.message_service.dto.message.MessageRequestDTO;
import com.example.message_service.entity.Message;

public class MessageMapper {

    public static MessageDto convertToDTO(Message message) {
        MessageDto dto = new MessageDto();
        dto.setId(message.getId());
        dto.setFrom(message.getFromUser());
        dto.setContext(message.getContent());
        dto.setTime(message.getTime());
        return dto;
    }

}
