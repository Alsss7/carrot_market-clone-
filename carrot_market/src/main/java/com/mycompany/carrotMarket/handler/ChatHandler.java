package com.mycompany.carrotMarket.handler;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.mycompany.carrotMarket.chat.dto.ChatDTO;
import com.mycompany.carrotMarket.chat.service.ChatService;
import com.mycompany.carrotMarket.chat.vo.ChatVO;
import com.mycompany.carrotMarket.chat.vo.MessageVO;

public class ChatHandler extends TextWebSocketHandler {
	private static final Logger logger = LoggerFactory.getLogger(ChatHandler.class);
	private static final Logger fileLogger = LoggerFactory.getLogger("fileLogger");

	private final Map<Integer, Set<WebSocketSession>> chatRoomSessionMap = new HashMap<Integer, Set<WebSocketSession>>();

	@Autowired
	private ChatService chatService;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("#ChatHandler, afterConnectionEstablished");
		if (session.getAttributes().get("chatId") != null) {
			int chatId = (Integer) session.getAttributes().get("chatId");
			if (!chatRoomSessionMap.containsKey(chatId)) {
				chatRoomSessionMap.put(chatId, new HashSet<WebSocketSession>());
			}
			Set<WebSocketSession> chatRoomSessions = chatRoomSessionMap.get(chatId);

			if (!chatRoomSessions.contains(session)) {
				chatRoomSessions.add(session);
			}

			for (int key : chatRoomSessionMap.keySet()) {
				logger.info("----------------------------");
				logger.info("chatId : {}", key);
				logger.info("----------������ ���----------");
				for (WebSocketSession s : chatRoomSessionMap.get(key)) {
					logger.info("{}", s.getPrincipal().getName());
				}
			}
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		logger.info("#ChatHandler, handleMessage");
		String loginId = session.getPrincipal().getName();

		JSONObject object = new JSONObject(message.getPayload());
		int productId = object.getInt("productId");
		String buyerId = object.getString("buyerId");
		String sellerId = object.getString("sellerId");
		String msg = object.getString("msg");

		int chatId;
		if (session.getAttributes().get("chatId") != null) {
			chatId = (Integer) session.getAttributes().get("chatId");
		}

		ChatDTO chatDTO = new ChatDTO(sellerId, buyerId, productId);
		ChatVO chat = chatService.selectChat(chatDTO);
		if (chat == null) {
			logger.info("chat�� �������� �ʽ��ϴ�. ���ο� chat�� �����մϴ�.");
			ChatVO newChat = new ChatVO(productId, sellerId, buyerId);
			logger.info("���ο� chat ����");
			logger.info("newChat : " + newChat.toString());
			boolean result = chatService.insertChat(newChat);
			if (result) {
				logger.info("���ο� ä���� �����Ǿ����ϴ�.");
				chat = chatService.selectChat(chatDTO);
				chatId = chat.getChatId();
				if (!chatRoomSessionMap.containsKey(chatId)) {
					chatRoomSessionMap.put(chatId, new HashSet<WebSocketSession>());
				}

				Set<WebSocketSession> chatRoomSessions = chatRoomSessionMap.get(chatId);
				if (!chatRoomSessions.contains(session)) {
					chatRoomSessions.add(session);
				}

				for (int key : chatRoomSessionMap.keySet()) {
					logger.info("----------------------------");
					logger.info("chatId : {}", key);
					logger.info("----------������ ���----------");
					for (WebSocketSession s : chatRoomSessionMap.get(key)) {
						logger.info("{}", s.getPrincipal().getName());
					}
				}
			} else {
				logger.info("���ο� ä���� �������� �ʾҽ��ϴ�.");
			}
		} else {
			logger.info("chat�� �����մϴ�. �޽��� �������� �Ѿ�ϴ�.");
		}

		MessageVO msgVO = new MessageVO(chat.getChatId(), loginId, msg);
		logger.info("���ο� message ����");
		logger.info("message : " + msgVO.toString());
		boolean result = chatService.insertMessage(msgVO);
		if (result) {
			logger.info("�޽����� ����Ǿ����ϴ�.");
		} else {
			logger.info("�޽����� ������� �ʾҽ��ϴ�.");
		}

		chatId = chat.getChatId();
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
				logger.info("{}���� �����Ͽ����ϴ�.", session.getPrincipal().getName());
			}
			if (chatRoomSessionMap.get(chatId).size() == 0) {
				chatRoomSessionMap.remove(chatId);
				logger.info("{}ä�ù��� �����ڰ� ��� �����Ͽ� ä�ù��� �����մϴ�.", chatId);
			}
		}
	}

	public static void main(String[] args) {
		logger.info("ChatHandler");
		fileLogger.info("fileLogger logger point");
	}
}
