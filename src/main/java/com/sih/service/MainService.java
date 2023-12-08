package com.sih.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sih.controller.vo.StockInVo;
import com.sih.controller.vo.StockOutVo;
import com.sih.controller.vo.WordInVo;
import com.sih.controller.vo.WordOutVo;
import com.sih.dao.SihDao;
import com.sih.dao.dto.StockDto;
import com.sih.dao.dto.WordDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MainService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final SihDao sihDao;
	
	/*
	 * 설명 : 단어 리스트 조회 서비스(워드클라우드용)
	 */
	public List<WordOutVo> selectWordList(){
		
		logger.info("selectWordList service start");
		
//		WordDto wordInDto = new WordDto();
		
		List<WordDto> wordDtoList = sihDao.selectWordList();
		
		List<WordOutVo> wordOutVoList = new ArrayList<WordOutVo>();
		
		for (WordDto word : wordDtoList) {
			WordOutVo wordOutVo = new WordOutVo();
			
			wordOutVo.setWordId(word.getWordId());
			wordOutVo.setWordContent(word.getWordContent());
			wordOutVo.setWordCount(word.getWordCount());
			
			wordOutVoList.add(wordOutVo);
		}
		
		logger.info("selectWordList service end");
		
		return wordOutVoList;
	}
	
	/*
	 * 설명 : 주식 리스트 조회 서비스(메뉴용)
	 */
	public List<StockOutVo> selectStockList(){
		
		logger.info("selectStockList service start");
		
//		WordDto wordInDto = new WordDto();
		
		List<StockDto> stockDtoList = sihDao.selectStockList();
		
		List<StockOutVo> stockOutVoList = new ArrayList<StockOutVo>();
		
		for (StockDto stock : stockDtoList) {
			StockOutVo stockOutVo = new StockOutVo();
			
			stockOutVo.setStockId(stock.getStockId());
			stockOutVo.setStockThema(stock.getStockThema());
			stockOutVo.setStockValue(stock.getStockValue());
			
			stockOutVoList.add(stockOutVo);
		}
		
		logger.info("selectStockList service end");
		
		return stockOutVoList;
	}

}
