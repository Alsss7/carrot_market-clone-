package com.mycompany.carrotMarket.chat.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.chat.dto.ChatDTO;
import com.mycompany.carrotMarket.chat.vo.ChatVO;
import com.mycompany.carrotMarket.chat.vo.MessageVO;

public interface ChatDAO {
	public ChatVO selectChat(ChatDTO chatDTO) throws DataAccessException;

	public ChatVO selectChatByChatId(int chatId) throws DataAccessException;

	public int insertChat(ChatVO chatVO) throws DataAccessException;

	public int deleteChatById(int chatId) throws DataAccessException;

	public int deleteChatByChatDTO(ChatDTO chatDTO) throws DataAccessException;

	public int deleteChatByProductId(int productId) throws DataAccessException;

	public int deleteMsgByChatId(int chatId) throws DataAccessException;

	public List<ChatVO> selectChatListByProductId(int productId) throws DataAccessException;

	public List<MessageVO> selectMessagesByChatId(int chatId) throws DataAccessException;

	public int selectMessagesCountByChatId(int chatId) throws DataAccessException;

	public int insertMessage(MessageVO messageVO) throws DataAccessException;

	public int updateLastMessageDate(int chatId) throws DataAccessException;

}
