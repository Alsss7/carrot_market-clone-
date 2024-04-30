package com.mycompany.carrotMarket.trade.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.mycompany.carrotMarket.trade.dao.TradeDAO;
import com.mycompany.carrotMarket.trade.vo.TradeVO;

@Repository
public class TradeDAOImpl implements TradeDAO {

	@Autowired
	SqlSession sqlSession;

	@Override
	public int insertTrade(TradeVO trade) throws DataAccessException {
		int result = sqlSession.insert("mappers.trade.insertTrade", trade);
		return result;
	}

	@Override
	public TradeVO selectTrade(int tradeId) throws DataAccessException {
		TradeVO trade = sqlSession.selectOne("mappers.trade.selectTrade", tradeId);
		return trade;
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
	public int updateTradeByProductId(TradeVO trade) throws DataAccessException {
		int result = sqlSession.update("mappers.trade.updateTradeByProductId", trade);
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
