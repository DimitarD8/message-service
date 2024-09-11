package com.example.message_service.dto.message;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageRequestDTO {
    private Long fromUserId;
    private String content;

    // Constructors, Getters, Setters
}
