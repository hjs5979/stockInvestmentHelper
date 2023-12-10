package com.sih.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.sih.controller.vo.BoardInVo;
import com.sih.controller.vo.BoardInqInVo;
import com.sih.controller.vo.BoardOutVo;
import com.sih.controller.vo.PageOutVo;
import com.sih.controller.vo.StockInVo;
import com.sih.controller.vo.StockOutVo;
import com.sih.controller.vo.UserInVo;
import com.sih.controller.vo.WordInVo;
import com.sih.controller.vo.WordOutVo;
import com.sih.dao.SihDao;
import com.sih.dao.dto.BoardDto;
import com.sih.dao.dto.BoardInqDto;
import com.sih.dao.dto.StockDto;
import com.sih.dao.dto.WordDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final SihDao sihDao;
	
	/*
	 * 설명 : 게시판 리스트 조회 서비스
	 */
	public PageOutVo selectBoardList(BoardInqInVo boardInqInVo){
		
		logger.info("===================================================================");
		logger.info("================== selectBoardList service start ==================");
		
		if(boardInqInVo.getInqCndtCon() == null) {
			throw new RuntimeException("selectBoardList service 입력조건 [ inqCndtCon ]");
		}
		if(boardInqInVo.getInqCndtDcd() == null) {
			throw new RuntimeException("selectBoardList service 입력조건 [ inqCndtDcd ]");
		}
		
		BoardInqDto boardInqDto = new BoardInqDto();
		
		boardInqDto.setInqCndtDcd(boardInqInVo.getInqCndtDcd());
		boardInqDto.setInqCndtCon(boardInqInVo.getInqCndtCon());
		
		List<BoardDto> boardDtoList = sihDao.selectBoardList(boardInqDto);
		
		Integer countBoard = sihDao.countBoardList(boardInqDto);
		
		List<BoardOutVo> boardOutVoList = new ArrayList<BoardOutVo>();
		
		for (BoardDto board : boardDtoList) {
			BoardOutVo boardOutVo = new BoardOutVo();
			
			boardOutVo.setBoardId(board.getBoardId());
			boardOutVo.setBoardTitle(board.getBoardTitle());
			boardOutVo.setBoardYmd(board.getBoardYmd());
			boardOutVo.setBoardWrtId(board.getBoardWrtId());
			boardOutVo.setBoardContent(board.getBoardContent());
			
			boardOutVoList.add(boardOutVo);
		}
		
		
		PageOutVo pageOutVo = new PageOutVo();
		
		pageOutVo.setCountBoard(countBoard);
		pageOutVo.setContent(boardOutVoList);
		
		logger.info("============ selectBoardList service end =============");
		logger.info("======================================================");
		return pageOutVo;
	}
	
	/*
	 * 설명 : 게시판 상세 조회 서비스
	 */
	public BoardOutVo selectBoardDetail(BoardInVo boardInVo){
		
		logger.info("===================================================================");
		logger.info("================== selectBoardDetail service start ==================");
		
		if(boardInVo.getBoardId() == null) {
			throw new RuntimeException("selectBoardDetail service 입력조건 [ boardId ]");
		}
		
		BoardDto boardInDto = new BoardDto();
		boardInDto.setBoardId(boardInVo.getBoardId());
		
		BoardDto boardOutDto = sihDao.selectBoardDetail(boardInDto);
		
		BoardOutVo boardOutVo = new BoardOutVo();
		
		boardOutVo.setBoardId(boardOutDto.getBoardId());
		boardOutVo.setBoardTitle(boardOutDto.getBoardTitle());
		boardOutVo.setBoardContent(boardOutDto.getBoardContent());
		boardOutVo.setBoardWrtId(boardOutDto.getBoardWrtId());
		boardOutVo.setBoardYmd(boardOutDto.getBoardYmd());
		
		logger.info("============ selectBoardDetail service end =============+");
		logger.info("=========================================================");
		return boardOutVo;
	}


}
