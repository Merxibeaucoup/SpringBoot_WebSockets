package com.edgar.websockets.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
	
	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
		
		/* register end point where the connection will take place*/
        registry.addEndpoint("/ws")
        
        /* allow origins -> base URL of client */
        .setAllowedOrigins("http://localhost:8443")
        
        /* enable sockJS for fall back options*/
        .withSockJS();
    }
	
	@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
    
        /* Set prefix for the end point that the client listens for our messages from */
        registry.enableSimpleBroker("/topic");
        
        /* Set prefix for end points the client will send messages to */
        registry.setApplicationDestinationPrefixes("/app");
	}

}
