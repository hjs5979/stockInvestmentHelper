package com.sih.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sih.controller.vo.DetailOutVo;
import com.sih.controller.vo.StockOutVo;
import com.sih.controller.vo.WordInVo;
import com.sih.controller.vo.WordOutVo;
import com.sih.service.DetailService;
import com.sih.service.MainService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final MainService mainService;
	
	private final DetailService detailService;
	
	@GetMapping("/word")
	public List<WordOutVo> selectWordList() {
		
		logger.info("selectWordList controller start");
		
		List<WordOutVo> wordOutVo = mainService.selectWordList();
			
		logger.info("selectWordList controller end");
		
		return wordOutVo;
	}
	
	@GetMapping("/stock")
	public List<StockOutVo> selectStockList() {
		
		logger.info("selectStockList controller start");
		
		List<StockOutVo> stockOutVo = mainService.selectStockList();
			
		logger.info("selectStockList controller end");
		
		return stockOutVo;
	}
	
	@PostMapping("/detail")
	public List<DetailOutVo> selectDetailList(@RequestBody WordInVo wordInVo) {
		
		logger.info("selectDetailList controller start");
			
		List<DetailOutVo> detailOutVo = detailService.selectDetailList(wordInVo);
			
		logger.info("selectDetailList controller end");
		
		return detailOutVo;
	}
	
}
