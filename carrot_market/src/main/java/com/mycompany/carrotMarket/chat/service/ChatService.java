package com.mycompany.carrotMarket.chat.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.chat.vo.ChatVO;

public interface ChatService {
	public ChatVO getChat(int productId, String buyerId) throws DataAccessException;

	public ChatVO getChatByChatId(int chatId) throws DataAccessException;

	public boolean addChat(ChatVO chatVO) throws DataAccessException;

	public boolean removeChatById(int chatId) throws DataAccessException;

	public boolean removeChat(int productId, String buyerId) throws DataAccessException;

	public boolean removeChatByProductId(int productId) throws DataAccessException;

	public List<ChatVO> getChatListByProductId(int productId) throws DataAccessException;

	public int getMessagesCountByChatId(int chatId) throws DataAccessException;
}
