package com.mycompany.carrotMarket.trade.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.carrotMarket.trade.service.TradeService;
import com.mycompany.carrotMarket.trade.vo.TradeVO;

@RestController
@RequestMapping(value = "/trade")
public class TradeController {
	@Autowired
	TradeService tradeService;

	@RequestMapping(value = "/getTradeInfo/{productId}", method = RequestMethod.GET)
	public ResponseEntity<Map<String, Integer>> getTradeInfo(@PathVariable int productId) throws Exception {
		Map<String, Integer> response = new HashMap<String, Integer>();
		TradeVO trade = tradeService.selectTradeByProductId(productId);
		response.put("tradeId", trade.getTradeId());
		return ResponseEntity.ok(response);
	}
}
