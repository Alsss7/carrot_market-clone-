package com.mycompany.carrotMarket.chat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.chat.dao.ChatDAO;
import com.mycompany.carrotMarket.chat.dto.ChatDTO;
import com.mycompany.carrotMarket.chat.service.ChatService;
import com.mycompany.carrotMarket.chat.vo.ChatVO;
import com.mycompany.carrotMarket.chat.vo.MessageVO;

@Service
public class ChatServiceImpl implements ChatService {

	@Autowired
	private ChatDAO chatDAO;

	@Autowired
	private ArticleService articleService;

	@Override
	public ChatVO selectChat(ChatDTO chatDTO) throws DataAccessException {
		ChatVO chat = chatDAO.selectChat(chatDTO);
		return chat;
	}

	@Override
	@Transactional
	public boolean insertChat(ChatVO chatVO) throws DataAccessException {
		int result1 = chatDAO.insertChat(chatVO);
		int result2 = articleService.increaseChat(chatVO.getProductId());
		if (result1 != 0 && result2 != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean deleteChatByProductId(int productId) throws DataAccessException {
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
	public List<ChatVO> selectChatListByProductId(int productId) throws DataAccessException {
		List<ChatVO> list = chatDAO.selectChatListByProductId(productId);
		return list;
	}

	@Override
	public List<MessageVO> selectMessagesByChatId(int chatId) throws DataAccessException {
		List<MessageVO> list = chatDAO.selectMessagesByChatId(chatId);
		return list;
	}

	@Override
	public boolean insertMessage(MessageVO messageVO) throws DataAccessException {
		int result = chatDAO.insertMessage(messageVO);
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

}
