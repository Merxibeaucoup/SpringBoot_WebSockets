package com.edgar.websockets.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.edgar.websockets.models.ChatMessage;
import com.edgar.websockets.models.Message;
import com.edgar.websockets.models.SocketResponse;
import com.edgar.websockets.models.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final SimpMessagingTemplate messagingTemplate;

	@MessageMapping("/user")
	@SendTo("/topic/user")
	public ResponseEntity<SocketResponse> getUser(User user) {
		messagingTemplate.convertAndSend("/topic/user" + new SocketResponse("Hi " + user.getUserName()));
		return ResponseEntity.ok().build();
	}

	// send massage publicly
	@MessageMapping("/chat.sendMessage") /* uses --> /app/chat.sendMessage */
	@SendTo("/topic/public")
	public Message sendMessage(@Payload Message message) {
		return message;

	}
	
	//send private message to private individual
	@MessageMapping("/chat.private-message")
	public Message receivePrivateMessage(@Payload Message message) {	
		
		/* use to listen  --> /user/username/private */
		messagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private", message);
		return message;
	}
	
	
	// add user
		@MessageMapping("/chat.addUser")
		@SendTo("/topic/public")
		public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
			
			//adds username in websocket session			
			headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
			return chatMessage;

		}
		
		
	

}
