package com.example.message_service.controller;

import com.example.message_service.dto.message.MessageDto;
import com.example.message_service.dto.message.MessageRequestDTO;
import com.example.message_service.entity.Message;
import com.example.message_service.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@CrossOrigin("*")
@AllArgsConstructor
public class MessageController {
    private final MessageService messageService;


    @GetMapping
    public ResponseEntity<List<MessageDto>> getMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @PostMapping
    public ResponseEntity<MessageDto> sendMessage(@RequestBody MessageRequestDTO messageRequest) {
        return ResponseEntity.ok(messageService.sendMessage(messageRequest));
    }
}
