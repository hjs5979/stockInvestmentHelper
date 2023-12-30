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
import com.sih.controller.vo.BoardInqInVo;
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
	
	/*
	 * 설명 : 게시판 리스트 조회 컨트롤러
	 */
	@PostMapping("/list")
	public PageOutVo selectWordList(@RequestBody BoardInqInVo boardInqInVo) {
		logger.info("===========================================================");
		logger.info("============ selectBoardList controller start =============");
		
		if (boardInqInVo == null) {
			throw new RuntimeException("selectBoarddList Controller 입력조건");
		}
		
		PageOutVo pageOutVo = boardService.selectBoardList(boardInqInVo);
			
		logger.info("============ selectBoardList controller end ============");
		logger.info("========================================================");
		return pageOutVo;
	}
	
	/*
	 * 설명 : 게시판 상세 조회 컨트롤러
	 */
	@PostMapping("/detail")
	public BoardOutVo selectBoardDetail(@RequestBody BoardInVo boardInVo) {
		logger.info("===========================================================");
		logger.info("======== selectDetailList controller start ================");
			
		BoardOutVo boardDetailOutVo = boardService.selectBoardDetail(boardInVo);
			
		logger.info("========== selectDetailList controller end ================");
		logger.info("===========================================================");
		return boardDetailOutVo;
	}
	
	/*
	 * 설명 : 게시판 등록 컨트롤러
	 */
	@PostMapping("/reg")
	public BoardOutVo regBoard(@RequestBody BoardInVo boardInVo) {
		logger.info("===========================================================");
		logger.info("======== selectDetailList controller start ================");
			
		BoardOutVo boardOutVo = boardService.regBoard(boardInVo);
			
		logger.info("========== selectDetailList controller end ================");
		logger.info("===========================================================");
		return boardOutVo;
	}
	
}
