package com.example.message_service.dto.message;

import com.example.message_service.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto {
    private Long id;
    private User from;
    private String context;
    private LocalDateTime time;

}
