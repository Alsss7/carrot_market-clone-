package com.mycompany.carrotMarket.chat.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycompany.carrotMarket.chat.dao.ChatDAO;
import com.mycompany.carrotMarket.chat.dto.ChatDTO;
import com.mycompany.carrotMarket.chat.vo.ChatVO;
import com.mycompany.carrotMarket.chat.vo.MessageVO;

@Repository
public class ChatDAOImpl implements ChatDAO {

	@Autowired
	SqlSession sqlSession;

	@Override
	public ChatVO selectChat(ChatDTO chatDTO) throws DataAccessException {
		ChatVO chat = sqlSession.selectOne("mappers.chat.selectChat", chatDTO);
		return chat;
	}

	@Override
	public int insertChat(ChatVO chatVO) throws DataAccessException {
		int result = sqlSession.insert("mappers.chat.insertChat", chatVO);
		return result;
	}

	@Override
	public List<ChatVO> selectChatListByProductId(int productId) throws DataAccessException {
		List<ChatVO> list = sqlSession.selectList("mappers.chat.selectChatListByProductId", productId);
		return list;
	}

	@Override
	public List<MessageVO> selectMessagesByChatId(int chatId) throws DataAccessException {
		List<MessageVO> list = sqlSession.selectList("mappers.chat.selectMessagesByChatId", chatId);
		return list;
	}

	@Override
	public int insertMessage(MessageVO messageVO) throws DataAccessException {
		int result = sqlSession.insert("mappers.chat.insertMessage", messageVO);
		return result;
	}

}
