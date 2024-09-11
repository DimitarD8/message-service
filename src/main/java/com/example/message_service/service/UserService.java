package com.example.message_service.service;

import com.example.message_service.dto.user.UserDTO;
import com.example.message_service.entity.User;
import com.example.message_service.exception.NotFoundException;
import com.example.message_service.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAllAdmins() {
        return userRepository.findAll().stream().filter(user ->
                user.getRole().equals("ADMIN")
        ).collect(Collectors.toList());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUserName(String userName){
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new NotFoundException("User with username not found!" + userName));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUserName());
        dto.setRole(user.getRole());
        return dto;
    }
    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
