package com.mycompany.carrotMarket.chat.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.chat.vo.MessageVO;

public interface MessageService {
	public List<MessageVO> getMessagesByChatId(int chatId) throws DataAccessException;

	public LocalDate getLastMessageDate(int chatId) throws DataAccessException;

	public boolean addMessage(MessageVO messageVO) throws DataAccessException;
}
