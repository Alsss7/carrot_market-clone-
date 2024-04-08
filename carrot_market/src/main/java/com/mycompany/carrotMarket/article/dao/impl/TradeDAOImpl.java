package com.mycompany.carrotMarket.article.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycompany.carrotMarket.article.dao.TradeDAO;
import com.mycompany.carrotMarket.article.dto.TradeDTO;
import com.mycompany.carrotMarket.article.vo.TradeVO;

@Repository
public class TradeDAOImpl implements TradeDAO {

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertTrade(TradeDTO dto) throws DataAccessException {
		int result = sqlSession.insert("mappers.trade.insertTrade", dto);
		return result;
	}

	@Override
	public TradeVO selectTradeByProductId(int productId) throws DataAccessException {
		TradeVO trade = sqlSession.selectOne("mappers.trade.selectTradeByProductId", productId);
		return trade;
	}

	@Override
	public List<Integer> selectTradeByBuyerId(String buyerId) throws DataAccessException {
		List<Integer> list = sqlSession.selectList("mappers.trade.selectTradeByBuyerId", buyerId);
		return list;
	}

	@Override
	public int updateTradeByProductId(TradeDTO dto) throws DataAccessException {
		int result = sqlSession.update("mappers.trade.updateTradeByProductId", dto);
		return result;
	}

	@Override
	public int deleteTradeByTradeId(int tradeId) throws DataAccessException {
		int result = sqlSession.delete("mappers.trade.deleteTradeByTradeId", tradeId);
		return result;
	}

	@Override
	public int deleteTradeByProductId(int productId) throws DataAccessException {
		int result = sqlSession.delete("mappers.trade.deleteTradeByProductId", productId);
		return result;
	}

}
