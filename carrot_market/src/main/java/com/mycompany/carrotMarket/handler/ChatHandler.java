package com.mycompany.carrotMarket.handler;

import java.util.HashMap;
import java.util.Map;

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

	private Map<String, WebSocketSession> sessionMap = new HashMap<String, WebSocketSession>();

	@Autowired
	private ChatService chatService;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		logger.info("#ChatHandler, afterConnectionEstablished");
		String loginId = session.getPrincipal().getName();
		sessionMap.put(loginId, session);
		logger.info(loginId + "님이 입장하셨습니다.");
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

		String targetId = null;
		if (loginId.equals(buyerId)) {
			targetId = sellerId;
		} else {
			targetId = buyerId;
		}

		if (sessionMap.get(targetId) != null) {
			sessionMap.get(targetId).sendMessage(new TextMessage(loginId + ":" + msg));
		}

		ChatDTO chatDTO = new ChatDTO(sellerId, buyerId, productId);
		ChatVO chat = chatService.selectChat(chatDTO);
		if (chat == null) {
			logger.info("chat이 존재하지 않습니다. 새로운 chat을 생성합니다.");
			ChatVO newChat = new ChatVO();
			logger.info("새로운 chat 생성");
			newChat.setProductId(productId);
			newChat.setSellerId(sellerId);
			newChat.setBuyerId(buyerId);
			logger.info("newChat productId: " + newChat.getProductId());
			logger.info("newChat sellerId: " + newChat.getSellerId());
			logger.info("newChat buyerId: " + newChat.getBuyerId());
			boolean result = chatService.insertChat(newChat);
			if (result) {
				logger.info("새로운 채팅이 생성되었습니다.");
				chat = chatService.selectChat(chatDTO);
			} else {
				logger.info("새로운 채팅이 생성되지 않았습니다.");
			}
		} else {
			logger.info("chat이 존재합니다. 메시지 생성으로 넘어갑니다.");
		}

		MessageVO msgVO = new MessageVO();
		logger.info("새로운 message 생성");
		msgVO.setChatId(chat.getChatId());
		msgVO.setSender(loginId);
		msgVO.setContent(msg);
		logger.info("message chatId: " + msgVO.getChatId());
		logger.info("message senderId: " + msgVO.getSender());
		logger.info("message content: " + msgVO.getContent());
		boolean result = chatService.insertMessage(msgVO);
		if (result) {
			logger.info("메시지가 저장되었습니다.");
		} else {
			logger.info("메시지가 저장되지 않았습니다.");
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		logger.info("#ChattingHandler, afterConnectionClosed");
		String loginId = session.getPrincipal().getName();
		sessionMap.remove(loginId);
		logger.info(loginId + "님이 퇴장하셨습니다.");
	}

	public static void main(String[] args) {
		logger.info("ChatHandler");
		fileLogger.info("fileLogger logger point");
	}
}
