package com.sist.web.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

@Service
public class ChatService {
	private final ChatClient chatClient;
	
	public ChatService(ChatClient.Builder chatClientBuild) {
		this.chatClient = chatClientBuild.build();
	}
	
	public Flux<String> streamChat(String userMessage) {
		return chatClient
				.prompt()
				.user(userMessage)
				.stream()
				.content()
				.bufferUntil(t -> t.endsWith(".") || t.endsWith("다") || t.endsWith("요"))
				.map(list -> String.join("", list))
				.doOnNext(System.out::println);
	}
}