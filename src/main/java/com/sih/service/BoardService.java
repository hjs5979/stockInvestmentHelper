package com.sih.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sih.controller.vo.BoardInVo;
import com.sih.controller.vo.BoardOutVo;
import com.sih.controller.vo.PageOutVo;
import com.sih.controller.vo.StockInVo;
import com.sih.controller.vo.StockOutVo;
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
	
	public PageOutVo selectBoardList(BoardInVo boardInVo){
		
		logger.info("selectBoardList service start");
		
		BoardInqDto boardInqDto = new BoardInqDto();
		
		boardInqDto.setInqCndtDcd(boardInVo.getInqCndtDcd());
		boardInqDto.setInqCndtCon(boardInVo.getInqCndtCon());
		boardInqDto.setBoardTitle(boardInVo.getBoardTitle());
		boardInqDto.setBoardContent(boardInVo.getBoardContent());
		
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
		
		logger.info("selectBoardList service end");
		
		return pageOutVo;
	}


}
