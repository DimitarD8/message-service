package com.example.message_service.service;

import com.example.message_service.dto.message.MessageDto;
import com.example.message_service.dto.message.MessageRequestDTO;
import com.example.message_service.entity.Message;
import com.example.message_service.entity.User;
import com.example.message_service.mapper.message.MessageMapper;
import com.example.message_service.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    private final UserService userService;

    public Message create(Message message){
        return this.messageRepository.save(message);
    }

    public List<Message> findAll(){
        return this.messageRepository.findAll();
    }

    public void deleteById(Long id){
        this.messageRepository.deleteById(id);
    }



    public List<MessageDto> getAllMessages() {
        return messageRepository.findAll().stream()
                .map(MessageMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public MessageDto sendMessage(MessageRequestDTO messageRequest) {
        User fromUser = userService.findById(messageRequest.getFromUserId());

        Message message = new Message();
        message.setFromUser(fromUser);
        message.setContent(messageRequest.getContent());
        message.setTime(LocalDateTime.now());
        messageRepository.save(message);
        MessageDto messageDto = MessageMapper.convertToDTO(message);
        messageDto.setFrom(fromUser);

        return messageDto;
    }


}
