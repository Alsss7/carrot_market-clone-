package com.mycompany.carrotMarket.article.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.mycompany.carrotMarket.article.dto.TradeDTO;
import com.mycompany.carrotMarket.article.vo.TradeVO;

public interface TradeDAO {
	public int insertTrade(TradeDTO dto) throws DataAccessException;

	public TradeVO selectTradeByProductId(int productId) throws DataAccessException;

	public List<Integer> selectTradeByBuyerId(String buyerId) throws DataAccessException;

	public int updateTradeByProductId(TradeDTO dto) throws DataAccessException;

	public int deleteTradeByTradeId(int tradeId) throws DataAccessException;

	public int deleteTradeByProductId(int productId) throws DataAccessException;
}
