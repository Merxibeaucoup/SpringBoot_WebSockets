package com.edgar.websockets.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.edgar.websockets.models.ChatMessage;
import com.edgar.websockets.models.enums.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventListener {
	
	private final SimpMessageSendingOperations messageSendingOperations;
	
	
	// handles when a user has left the chat and sends notification to destination 
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent disconnectEvent) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(disconnectEvent.getMessage());
		
		String username = (String) headerAccessor.getSessionAttributes().get("username");
		
		if(username !=null) {
			log.info("user disconnected: {}", username);
			
			var chatMessage = ChatMessage.builder()
					.type(MessageType.LEAVE)
					.sender(username)
					.build();
			
			messageSendingOperations.convertAndSend("/topic/public", chatMessage);
		}
	}

}
