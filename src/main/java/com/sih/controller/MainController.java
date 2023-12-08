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
	
	/*
	 * 설명 : 메인 - 단어 조회 컨트롤러(워드클라우드용)
	 */
	@GetMapping("/word")
	public List<WordOutVo> selectWordList() {
		
		logger.info("============ selectWordList controller start ============");
		
		List<WordOutVo> wordOutVo = mainService.selectWordList();
			
		logger.info("============ selectWordList controller end ============");
		
		return wordOutVo;
	}
	
	/*
	 * 설명 : 메인 - 주식 리스트 조회 컨트롤러(메뉴용)
	 */
	@GetMapping("/stock")
	public List<StockOutVo> selectStockList() {
		logger.info("=========================================================");
		logger.info("============ electStockList controller start ============");
		
		List<StockOutVo> stockOutVo = mainService.selectStockList();
			
		logger.info("============ selectStockList controller end ============");
		logger.info("========================================================");
		return stockOutVo;
	}
	
	/*
	 * 설명 : 메인 - 단어선택 시 상세조회 컨트롤러
	 */
	@PostMapping("/detail")
	public List<DetailOutVo> selectDetailList(@RequestBody WordInVo wordInVo) {
		logger.info("==========================================================");
		logger.info("============ selectDetailList controller start ============");
		
		if (wordInVo == null) {
			throw new RuntimeException("selectDetailList Controller 입력조건");
		}
		
		List<DetailOutVo> detailOutVo = detailService.selectDetailList(wordInVo);
			
		logger.info("============ selectDetailList controller end ============");
		logger.info("=========================================================");
		return detailOutVo;
	}
	
}
