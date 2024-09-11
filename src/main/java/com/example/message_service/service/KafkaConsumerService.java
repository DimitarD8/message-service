package com.example.message_service.service;

import com.example.message_service.entity.User;
import com.example.message_service.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KafkaConsumerService {

    private final UserRepository userRepository;
    private String secretKey;

    public KafkaConsumerService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "secretKeyTopic", groupId = "jwt-consumer-group")
    public void listen(String secretKey) {
        System.out.println("The secret key is: " + secretKey);
        this.secretKey = secretKey;
    }

    @KafkaListener(topics = "userDetailsTopic", groupId = "jwt-consumer-group")
    public void listenForUserDetails(String userDetailsMessage) {
        System.out.println(userDetailsMessage);
        String[] userNameAndRole = userDetailsMessage.substring(1, userDetailsMessage.length()-1).split(",");
        String userName = userNameAndRole[0];
        System.out.println(userName);
        String role = userNameAndRole[1];
        System.out.println(role);
        Optional<User> userOptional = userRepository.findByUserName(userName);
        if (userOptional.isEmpty()) {
            User newUser = new User();
            newUser.setUserName(userName);
            newUser.setRole(role);
            userRepository.save(newUser);
            System.out.println("Created new user: " + userName);
        } else {
            System.out.println("User already exists: " + userName);
        }
    }


    public String getSecretKey() {
        return secretKey.substring(1, secretKey.length()-1);
    }
}
