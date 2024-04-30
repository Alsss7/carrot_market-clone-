package com.mycompany.carrotMarket.trade.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.trade.vo.TradeVO;

public interface TradeDAO {
	public int insertTrade(TradeVO trade) throws DataAccessException;

	public TradeVO selectTrade(int tradeId) throws DataAccessException;

	public TradeVO selectTradeByProductId(int productId) throws DataAccessException;

	public List<Integer> selectTradeByBuyerId(String buyerId) throws DataAccessException;

	public int updateTradeByProductId(TradeVO trade) throws DataAccessException;

	public int deleteTradeByTradeId(int tradeId) throws DataAccessException;

	public int deleteTradeByProductId(int productId) throws DataAccessException;
}
