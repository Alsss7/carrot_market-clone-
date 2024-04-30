package com.mycompany.carrotMarket.chat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.chat.dao.ChatDAO;
import com.mycompany.carrotMarket.chat.service.ChatService;
import com.mycompany.carrotMarket.chat.vo.ChatVO;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatDAO chatDAO;

	@Autowired
	private ArticleService articleService;

	@Override
	public ChatVO getChat(int productId, String buyerId) throws DataAccessException {
		String loginId = SecurityContextHolder.getContext().getAuthentication().getName();
		return buyerId != null ? chatDAO.selectChat(productId, buyerId) : chatDAO.selectChat(productId, loginId);
	}

	@Override
	public ChatVO getChatByChatId(int chatId) throws DataAccessException {
		ChatVO chat = chatDAO.selectChatByChatId(chatId);
		return chat;
	}

	@Override
	@Transactional
	public boolean addChat(ChatVO chatVO) throws DataAccessException {
		int result = chatDAO.insertChat(chatVO);
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean removeChatById(int chatId) throws DataAccessException {
		ChatVO chat = getChatByChatId(chatId);

		int result1 = chatDAO.deleteMsgByChatId(chatId);
		int result2 = chatDAO.deleteChatById(chatId);
		int result3 = articleService.decreaseChat(chat.getProductId());
		if (result1 != 0 && result2 != 0 && result3 != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean removeChat(int productId, String buyerId) throws DataAccessException {
		ChatVO chat = getChat(productId, buyerId);

		int result1 = chatDAO.deleteMsgByChatId(chat.getChatId());
		int result2 = chatDAO.deleteChat(productId, buyerId);
		int result3 = articleService.decreaseChat(productId);
		if (result1 != 0 && result2 != 0 && result3 != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean removeChatByProductId(int productId) throws DataAccessException {
		List<ChatVO> chatList = chatDAO.selectChatListByProductId(productId);

		for (ChatVO chat : chatList) {
			chatDAO.deleteMsgByChatId(chat.getChatId());
		}
		int result = chatDAO.deleteChatByProductId(productId);

		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<ChatVO> getChatListByProductId(int productId) throws DataAccessException {
		List<ChatVO> list = chatDAO.selectChatListByProductId(productId);
		return list;
	}

	@Override
	public int getMessagesCountByChatId(int chatId) throws DataAccessException {
		int count = chatDAO.selectMessagesCountByChatId(chatId);
		return count;
	}

}
