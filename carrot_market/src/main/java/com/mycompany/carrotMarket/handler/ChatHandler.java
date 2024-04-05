package com.mycompany.carrotMarket.handler;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.mycompany.carrotMarket.chat.service.ChatService;
import com.mycompany.carrotMarket.chat.vo.MessageVO;

public class ChatHandler extends TextWebSocketHandler {
	private static final Logger logger = LoggerFactory.getLogger(ChatHandler.class);
	private static final Logger fileLogger = LoggerFactory.getLogger("fileLogger");

	private final Map<Integer, Set<WebSocketSession>> chatRoomSessionMap = new ConcurrentHashMap<Integer, Set<WebSocketSession>>();

	@Autowired
	private ChatService chatService;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("#ChatHandler, afterConnectionEstablished");

		int chatId;
		if (session.getAttributes().get("chatId") != null) {
			chatId = (Integer) session.getAttributes().get("chatId");
			if (!chatRoomSessionMap.containsKey(chatId)) {
				chatRoomSessionMap.put(chatId, new HashSet<WebSocketSession>());
			}
			Set<WebSocketSession> chatRoomSessions = chatRoomSessionMap.get(chatId);

			logger.info("sessionId : {}", session.getId());
			if (!chatRoomSessions.contains(session)) {
				chatRoomSessions.add(session);
				logger.info("{} 채팅방에 {}님이 입장하였습니다.", chatId, session.getPrincipal().getName());
			}
		}

		for (int key : chatRoomSessionMap.keySet()) {
			logger.info("----------------------------");
			logger.info("chatId : {}", key);
			logger.info("----------참가자 목록----------");
			for (WebSocketSession s : chatRoomSessionMap.get(key)) {
				logger.info("{}, {}", s.getId(), s.getPrincipal().getName());
			}
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		logger.info("#ChatHandler, handleMessage");
		String loginId = session.getPrincipal().getName();

		JSONObject object = new JSONObject(message.getPayload());
		String msg = object.getString("msg");

		int chatId = 0;
		if (session.getAttributes().get("chatId") != null) {
			chatId = (Integer) session.getAttributes().get("chatId");
		}

		MessageVO msgVO = new MessageVO(chatId, loginId, msg);
		logger.info("새로운 message 생성");
		logger.info("message : " + msgVO.toString());
		boolean result = chatService.insertMessage(msgVO);
		if (result) {
			logger.info("메시지가 저장되었습니다.");
		} else {
			logger.info("메시지가 저장되지 않았습니다.");
		}

		Set<WebSocketSession> chatRoomSessions = chatRoomSessionMap.get(chatId);
		for (WebSocketSession wsSession : chatRoomSessions) {
			logger.info("message send to {}", wsSession.getPrincipal().getName());
			wsSession.sendMessage(new TextMessage(loginId + ":" + msg));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info("#ChattingHandler, afterConnectionClosed");

		if (session.getAttributes().get("chatId") != null) {
			int chatId = (Integer) session.getAttributes().get("chatId");
			if (chatRoomSessionMap.containsKey(chatId)) {
				chatRoomSessionMap.get(chatId).remove(session);
				logger.info("{} 채팅방의 {}님이 퇴장하였습니다.", chatId, session.getPrincipal().getName());
			}
			if (chatRoomSessionMap.get(chatId).size() == 0) {
				chatRoomSessionMap.remove(chatId);
				logger.info("{}채팅방의 참가자가 모두 퇴장하여 채팅방을 삭제합니다.", chatId);
			}
		}
	}

	public static void main(String[] args) {
		logger.info("ChatHandler");
		fileLogger.info("fileLogger logger point");
	}
}
