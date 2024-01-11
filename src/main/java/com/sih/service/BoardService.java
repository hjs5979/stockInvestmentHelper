package com.sih.service;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
import com.sih.dao.dto.AtchDto;
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
	
	private final UserService userService;
	
	/*
	 * 설명 : 게시판 리스트 조회 서비스
	 */
	@Transactional
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
			boardOutVo.setBoardCretTs(board.getBoardCretTs());
			boardOutVo.setBoardWrtId(board.getBoardWrtId());
			boardOutVo.setBoardContent(board.getBoardContent());
			boardOutVo.setAtchNo(board.getAtchNo());
			boardOutVo.setAtchYn(board.getAtchYn());
			
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
	@Transactional
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
		boardOutVo.setBoardCretTs(boardOutDto.getBoardCretTs());
		
		if(boardOutDto.getAtchNo() != null) {
			AtchDto atchInDto = new AtchDto();
			atchInDto.setAtchNo(boardOutDto.getAtchNo());
			
			AtchDto atchBaseDto = sihDao.selectAtchBase(atchInDto);
			boardOutVo.setAtchCnt(atchBaseDto.getAtchCnt());
			boardOutVo.setAtchTtlSize(atchBaseDto.getAtchTtlSize());
			
			List<AtchDto> fileList = sihDao.selectAtchDtl(atchInDto);
			boardOutVo.setAtchNo(boardOutDto.getAtchNo());
			boardOutVo.setFileList(fileList);
			
		}
		
		logger.info("============ selectBoardDetail service end =============+");
		logger.info("=========================================================");
		return boardOutVo;
	}
	
	/*
	 * 설명 : 게시판 등록 서비스
	 */
	@Transactional
	public void regBoard(BoardInVo boardInVo){
		
		logger.info("===================================================================");
		logger.info("================== regBoard service start ==================");
		
		this.vrfcRegBoardInputValue(boardInVo);
		
		BoardDto boardInDto = new BoardDto();
		
		boardInDto.setBoardTitle(boardInVo.getBoardTitle());
		boardInDto.setBoardContent(boardInVo.getBoardContent());
		boardInDto.setBoardWrtId(boardInVo.getBoardWrtId());
		boardInDto.setBoardCretTs(LocalDateTime.now());
		boardInDto.setAtchNo(boardInVo.getAtchNo());
		
		sihDao.insertBoardDetail(boardInDto);
		
		logger.info("============ regBoard service end =============+");
		logger.info("=========================================================");
		
	}
	
	/*
	 * 설명 : 게시판 등록 입력값 체크 메서드
	 */
	private void vrfcRegBoardInputValue(BoardInVo boardInVo) {
		
		if(ObjectUtils.isEmpty(boardInVo.getBoardTitle())) {
			throw new RuntimeException("regBoard service 입력조건 [ boardTitle ]");
		}
		if(ObjectUtils.isEmpty(boardInVo.getBoardContent())) {
			throw new RuntimeException("regBoard service 입력조건 [ boardContent ]");
		}
		if(ObjectUtils.isEmpty(boardInVo.getBoardWrtId())) {
			throw new RuntimeException("regBoard service 입력조건 [ boardWrtId ]");
		}
		
	}
	
	/*
	 * 설명 : 게시판 수정 서비스
	 */
	@Transactional
	public void mdfcBoard(BoardInVo boardInVo){
		
		logger.info("===================================================================");
		logger.info("================== regBoard service start ==================");
		
		this.vrfcMdfcBoardInputValue(boardInVo);
		
		BoardDto boardInDto = new BoardDto();
		
		boardInDto.setBoardId(boardInVo.getBoardId());
		
		BoardDto boardOutDto = sihDao.selectForUpdateBoardDetail(boardInDto);
		
		boardOutDto.setBoardTitle(boardInVo.getBoardTitle());
		boardOutDto.setBoardContent(boardInVo.getBoardContent());
		boardOutDto.setAtchNo(boardInVo.getAtchNo());
		
		sihDao.updateBoardDetail(boardOutDto);
		
		logger.info("============ regBoard service end =============+");
		logger.info("=========================================================");
		
	}
	
	/*
	 * 설명 : 게시판 수정 입력값 체크 메서드
	 */
	private void vrfcMdfcBoardInputValue(BoardInVo boardInVo) {
		
		if(ObjectUtils.isEmpty(boardInVo.getBoardTitle())) {
			throw new RuntimeException("regBoard service 입력조건 [ boardTitle ]");
		}
		if(ObjectUtils.isEmpty(boardInVo.getBoardContent())) {
			throw new RuntimeException("regBoard service 입력조건 [ boardContent ]");
		}
		
	}
	
	/*
	 * 설명 : 게시판 삭제 서비스
	 */
	@Transactional
	public void deleteBoardDetail(BoardInVo boardInVo){
		
		logger.info("===================================================================");
		logger.info("================== deleteBoardDetail service start ==================");
		
//		this.vrfcMdfcBoardInputValue(boardInVo);
		
		BigInteger boardId = boardInVo.getBoardId();
		
		sihDao.deleteBoardDetail(boardId);
		
		logger.info("============ deleteBoardDetail service end =============+");
		logger.info("=========================================================");
		
	}


}
