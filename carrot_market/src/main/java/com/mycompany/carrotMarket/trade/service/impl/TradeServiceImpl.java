package com.mycompany.carrotMarket.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mycompany.carrotMarket.trade.dao.TradeDAO;
import com.mycompany.carrotMarket.trade.service.TradeService;
import com.mycompany.carrotMarket.trade.vo.TradeVO;

@Service
public class TradeServiceImpl implements TradeService {

	@Autowired
	TradeDAO tradeDAO;

	@Override
	public TradeVO selectTrade(int tradeId) throws DataAccessException {
		TradeVO trade = tradeDAO.selectTrade(tradeId);
		return trade;
	}

	@Override
	public TradeVO selectTradeByProductId(int productId) throws DataAccessException {
		TradeVO trade = tradeDAO.selectTradeByProductId(productId);
		return trade;
	}
}