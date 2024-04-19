package com.mycompany.carrotMarket.trade.service;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.trade.vo.TradeVO;

public interface TradeService {
	public TradeVO selectTrade(int tradeId) throws DataAccessException;

	public TradeVO selectTradeByProductId(int productId) throws DataAccessException;
}
