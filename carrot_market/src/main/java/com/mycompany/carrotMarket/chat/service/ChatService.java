package com.mycompany.carrotMarket.chat.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.chat.dto.ChatDTO;
import com.mycompany.carrotMarket.chat.vo.ChatVO;
import com.mycompany.carrotMarket.chat.vo.MessageVO;

public interface ChatService {
	public ChatVO selectChat(ChatDTO chatDTO) throws DataAccessException;

	public boolean insertChat(ChatVO chatVO) throws DataAccessException;
	
	public List<ChatVO> selectChatListByProductId(int productId) throws DataAccessException;

	public List<MessageVO> selectMessagesByChatId(int chatId) throws DataAccessException;

	public boolean insertMessage(MessageVO messageVO) throws DataAccessException;
}
