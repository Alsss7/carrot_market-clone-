package com.mycompany.carrotMarket.chat.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.carrotMarket.article.service.ArticleService;
import com.mycompany.carrotMarket.chat.dao.ChatDAO;
import com.mycompany.carrotMarket.chat.service.MessageService;
import com.mycompany.carrotMarket.chat.vo.ChatVO;
import com.mycompany.carrotMarket.chat.vo.MessageVO;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	ChatDAO chatDAO;

	@Autowired
	ArticleService articleService;

	@Override
	public List<MessageVO> getMessagesByChatId(int chatId) throws DataAccessException {
		return chatDAO.selectMessagesByChatId(chatId);
	}

	@Override
	public LocalDate getLastMessageDate(int chatId) throws DataAccessException {
		List<MessageVO> messages = getMessagesByChatId(chatId);
		if (messages.size() > 0) {
			Date date = messages.get(messages.size() - 1).getSentAt();
			return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		}
		return null;
	}

	@Override
	@Transactional
	public boolean addMessage(MessageVO messageVO) throws DataAccessException {
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
