package com.mycompany.carrotMarket.chat.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycompany.carrotMarket.chat.dao.ChatDAO;
import com.mycompany.carrotMarket.chat.vo.ChatVO;
import com.mycompany.carrotMarket.chat.vo.MessageVO;

@Repository
public class ChatDAOImpl implements ChatDAO {

	@Autowired
	SqlSession sqlSession;

	@Override
	public ChatVO selectChat(int productId, String buyerId) throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("buyerId", buyerId);

		return sqlSession.selectOne("mappers.chat.selectChat", map);
	}

	@Override
	public ChatVO selectChatByChatId(int chatId) throws DataAccessException {
		return sqlSession.selectOne("mappers.chat.selectChatByChatId", chatId);
	}

	@Override
	public int insertChat(ChatVO chatVO) throws DataAccessException {
		return sqlSession.insert("mappers.chat.insertChat", chatVO);
	}

	@Override
	public int deleteChatById(int chatId) throws DataAccessException {
		return sqlSession.delete("mappers.chat.deleteChatById", chatId);
	}

	@Override
	public int deleteChat(int productId, String buyerId) throws DataAccessException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productId", productId);
		map.put("buyerId", buyerId);

		return sqlSession.delete("mappers.chat.deleteChat", map);
	}

	@Override
	public int deleteChatByProductId(int productId) throws DataAccessException {
		return sqlSession.delete("mappers.chat.deleteChatByProductId", productId);
	}

	@Override
	public int deleteMsgByChatId(int chatId) throws DataAccessException {
		return sqlSession.delete("mappers.chat.deleteMsgByChatId", chatId);
	}

	@Override
	public List<ChatVO> selectChatListByProductId(int productId) throws DataAccessException {
		return sqlSession.selectList("mappers.chat.selectChatListByProductId", productId);
	}

	@Override
	public List<MessageVO> selectMessagesByChatId(int chatId) throws DataAccessException {
		return sqlSession.selectList("mappers.chat.selectMessagesByChatId", chatId);
	}

	@Override
	public int selectMessagesCountByChatId(int chatId) throws DataAccessException {
		return sqlSession.selectOne("mappers.chat.selectMessagesCountByChatId", chatId);
	}

	@Override
	public int insertMessage(MessageVO messageVO) throws DataAccessException {
		return sqlSession.insert("mappers.chat.insertMessage", messageVO);
	}

	@Override
	public int updateLastMessageDate(int chatId) throws DataAccessException {
		return sqlSession.update("mappers.chat.updateLastMessageDate", chatId);
	}

}
