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
	public ChatVO selectChatByChatId(int chatId) throws DataAccessException {
		ChatVO chat = chatDAO.selectChatByChatId(chatId);
		return chat;
	}

	@Override
	@Transactional
	public boolean insertChat(ChatVO chatVO) throws DataAccessException {
		int result = chatDAO.insertChat(chatVO);
		if (result != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	@Transactional
	public boolean deleteChatById(int chatId) throws DataAccessException {
		ChatVO chat = selectChatByChatId(chatId);

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
	public boolean deleteChatByChatDTO(ChatDTO chatDTO) throws DataAccessException {
		ChatVO chat = selectChat(chatDTO);

		int result1 = chatDAO.deleteMsgByChatId(chat.getChatId());
		int result2 = chatDAO.deleteChatByChatDTO(chatDTO);
		int result3 = articleService.decreaseChat(chatDTO.getProductId());
		if (result1 != 0 && result2 != 0 && result3 != 0) {
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
	public int selectMessagesCountByChatId(int chatId) throws DataAccessException {
		int count = chatDAO.selectMessagesCountByChatId(chatId);
		return count;
	}

	@Override
	@Transactional
	public boolean insertMessage(MessageVO messageVO) throws DataAccessException {
		int msgCount = chatDAO.selectMessagesCountByChatId(messageVO.getChatId());
		if (msgCount == 0) {
			ChatVO chat = chatDAO.selectChatByChatId(messageVO.getChatId());
			articleService.increaseChat(chat.getProductId());
		}
		int result1 = chatDAO.insertMessage(messageVO);
		int result2 = chatDAO.updateLastMessageDate(messageVO.getChatId());
		if (result1 != 0 && result2 != 0) {
			return true;
		} else {
			return false;
		}
	}

}
