package com.mediconnect.controller;

import com.mediconnect.dto.ChatMessageRequest;
import com.mediconnect.model.ChatMessage;
import com.mediconnect.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @GetMapping(value = {"/{orderId}", "/order/{orderId}"})
    public ResponseEntity<List<ChatMessage>> getChatMessages(@PathVariable Long orderId) {
        return ResponseEntity.ok(chatMessageRepository.findByOrderIdOrderByTimestampAsc(orderId));
    }

    @PostMapping
    public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessageRequest request) {
        ChatMessage msg = new ChatMessage(
            request.getOrderId(),
            request.getSenderRole(),
            request.getSenderName(),
            request.getMessage()
        );
        chatMessageRepository.save(msg);
        return ResponseEntity.ok(msg);
    }
}
