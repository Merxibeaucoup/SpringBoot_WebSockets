package com.edgar.websockets.models;

import java.time.LocalDateTime;

import com.edgar.websockets.models.enums.MessageType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {

	private String senderName;

	private String receiverName;

	private String message;

	private LocalDateTime date = LocalDateTime.now();

	private MessageType messageStatus;

}
