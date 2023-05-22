package com.edgar.websockets.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.edgar.websockets.models.SocketResponse;
import com.edgar.websockets.models.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {
	
	private final SimpMessagingTemplate messagingTemplate;
	
	@MessageMapping("/user")
	@SendTo("/topic/user")
	public ResponseEntity<SocketResponse> getUser(User user){
		messagingTemplate.convertAndSend("/topic/user"+ new SocketResponse("Hi "+ user.getUserName()));
		return ResponseEntity.ok().build();
	}

}
