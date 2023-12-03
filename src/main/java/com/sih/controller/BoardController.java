package com.sih.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sih.controller.vo.BoardInVo;
import com.sih.controller.vo.BoardOutVo;
import com.sih.controller.vo.DetailOutVo;
import com.sih.controller.vo.PageOutVo;
import com.sih.controller.vo.StockOutVo;
import com.sih.controller.vo.WordInVo;
import com.sih.controller.vo.WordOutVo;
import com.sih.service.BoardService;
import com.sih.service.DetailService;
import com.sih.service.MainService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final BoardService boardService;
	
	@PostMapping("/list")
	public PageOutVo selectWordList(@RequestBody BoardInVo boardInVo) {
		
		logger.info("selectBoardList controller start");
		
		PageOutVo pageOutVo = boardService.selectBoardList(boardInVo);
			
		logger.info("selectBoardList controller end");
		
		return pageOutVo;
	}
	
//	@PostMapping("/detail")
//	public List<DetailOutVo> selectDetailList(@RequestBody WordInVo wordInVo) {
//		
//		logger.info("selectDetailList controller start");
//			
//		List<DetailOutVo> detailOutVo = detailService.selectDetailList(wordInVo);
//			
//		logger.info("selectDetailList controller end");
//		
//		return detailOutVo;
//	}
	
}
