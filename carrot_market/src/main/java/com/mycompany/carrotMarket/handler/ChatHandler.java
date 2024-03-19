package com.mycompany.carrotMarket.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatHandler extends TextWebSocketHandler {

	private static final Logger logger = LoggerFactory.getLogger(ChatHandler.class);
	private static final Logger fileLogger = LoggerFactory.getLogger("fileLogger");

	public static void main(String[] args) {
		logger.info("ChatHandler");
		fileLogger.info("fileLogger logger point");
	}

	private List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("#ChatHandler, afterConnectionEstablished");
		sessionList.add(session);

		logger.info(session.getPrincipal().getName() + "¥‘¿Ã ¿‘¿Â«œºÃΩ¿¥œ¥Ÿ.");
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		logger.info("#ChatHandler, handleMessage");
		logger.info(session.getId() + ": " + message);

		for (WebSocketSession s : sessionList) {
			s.sendMessage(new TextMessage(session.getPrincipal().getName() + ":" + message.getPayload()));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		logger.info("#ChattingHandler, afterConnectionClosed");

		sessionList.remove(session);

		logger.info(session.getPrincipal().getName() + "¥‘¿Ã ≈¿Â«œºÃΩ¿¥œ¥Ÿ.");
	}

}
