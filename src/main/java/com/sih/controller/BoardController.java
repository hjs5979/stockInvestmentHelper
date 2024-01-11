package com.sih.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sih.controller.vo.AtchInVo;
import com.sih.controller.vo.BoardInVo;
import com.sih.controller.vo.BoardInqInVo;
import com.sih.controller.vo.BoardOutVo;
import com.sih.controller.vo.DetailOutVo;
import com.sih.controller.vo.PageOutVo;
import com.sih.controller.vo.StockOutVo;
import com.sih.controller.vo.TokenInVo;
import com.sih.controller.vo.WordInVo;
import com.sih.controller.vo.WordOutVo;
import com.sih.service.AtchService;
import com.sih.service.BoardService;
import com.sih.service.DetailService;
import com.sih.service.MainService;
import com.sih.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final BoardService boardService;
	
	private final UserService userService;
	
	private final AtchService atchService;
	
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
	public BoardOutVo selectBoardDetail(@RequestBody BoardInVo boardInVo, HttpServletRequest httpRequest) {
		logger.info("===========================================================");
		logger.info("======== selectDetailList controller start ================");
		
		TokenInVo tokenInVo = new TokenInVo();
		
		tokenInVo.setAccessToken(httpRequest.getHeader("accessToken"));
		tokenInVo.setRefreshToken(httpRequest.getHeader("refreshToken"));
		tokenInVo.setUserId(httpRequest.getHeader("userId"));
		
		userService.checkUser(tokenInVo);
		
		BoardOutVo boardDetailOutVo = boardService.selectBoardDetail(boardInVo);
			
		logger.info("========== selectDetailList controller end ================");
		logger.info("===========================================================");
		return boardDetailOutVo;
	}
	
	/*
	 * 설명 : 게시판 등록 컨트롤러
	 */
	@PostMapping("/reg")
	public void regBoard(@ModelAttribute BoardInVo boardInVo, HttpServletRequest httpRequest) throws IOException {
		logger.info("===========================================================");
		logger.info("============= regBoard controller start ===================");
		
		if (boardInVo == null) {
			throw new RuntimeException("regBoard Controller 입력조건");
		}
		
		TokenInVo tokenInVo = new TokenInVo();
		
		tokenInVo.setAccessToken(httpRequest.getHeader("accessToken"));
		tokenInVo.setRefreshToken(httpRequest.getHeader("refreshToken"));
		tokenInVo.setUserId(httpRequest.getHeader("userId"));
		
		userService.checkUser(tokenInVo);
		
		if (boardInVo.getFileList() != null ) {
			AtchInVo atchInVo = new AtchInVo();
			
			atchInVo.setFileList(boardInVo.getFileList());
			atchInVo.setAtchTtlSize(boardInVo.getAtchTtlSize());
			atchInVo.setAtchCnt(boardInVo.getAtchCnt());
			
			BigInteger atchNo = atchService.upload(atchInVo);
			
			boardInVo.setAtchNo(atchNo);
		}
		
		boardInVo.setBoardWrtId(tokenInVo.getUserId());
		
		boardService.regBoard(boardInVo);
			
		logger.info("================ regBoard controller end ====================");
		logger.info("=============================================================");
//		return boardOutVo;
	}
	
	/*
	 * 설명 : 게시판 수정 컨트롤러
	 */
	@PostMapping("/mdfc")
	public void mdfcBoard(@ModelAttribute BoardInVo boardInVo, HttpServletRequest httpRequest) throws IOException {
		logger.info("===========================================================");
		logger.info("======== selectDetailList controller start ================");
		
		if (boardInVo == null) {
			throw new RuntimeException("mdfcBoard Controller 입력조건");
		}

		TokenInVo tokenInVo = new TokenInVo();
		
		tokenInVo.setAccessToken(httpRequest.getHeader("accessToken"));
		tokenInVo.setRefreshToken(httpRequest.getHeader("refreshToken"));
		tokenInVo.setUserId(httpRequest.getHeader("userId"));
		
		userService.checkUser(tokenInVo);
		
		AtchInVo atchInVo = new AtchInVo();
		
		atchInVo.setFileList(boardInVo.getFileList());
		atchInVo.setCancelfiles(boardInVo.getCancelFiles());
		atchInVo.setAtchNo(boardInVo.getAtchNo());
		atchInVo.setAtchCnt(boardInVo.getAtchCnt());
		atchInVo.setAtchTtlSize(boardInVo.getAtchTtlSize());
		
		if(boardInVo.getAtchNo() != null &&  boardInVo.getCancelFiles() != null) {
			
			atchService.deleteAtchDtl(boardInVo.getCancelFiles());
			BigInteger atchNo = atchService.upload(atchInVo);
			boardInVo.setAtchNo(atchNo);
		}
		
		if (boardInVo.getFileList() != null && boardInVo.getCancelFiles() == null) {
			
			BigInteger atchNo = atchService.upload(atchInVo);
			boardInVo.setAtchNo(atchNo);
			
		}
		
		boardService.mdfcBoard(boardInVo);

			
		logger.info("========== selectDetailList controller end ================");
		logger.info("===========================================================");
//		return boardOutVo;
	}
	
	/*
	 * 설명 : 게시판 삭제 컨트롤러
	 */
	@PostMapping("/delete")
	public void deleteBoardDetail(@RequestBody BoardInVo boardInVo, HttpServletRequest httpRequest){
		logger.info("==================================================================");
		logger.info("=============== deleteBoardDetail controller start ================");
		
		if (boardInVo == null) {
			throw new RuntimeException("deleteBoardDetail Controller 입력조건");
		}

		TokenInVo tokenInVo = new TokenInVo();
		
		tokenInVo.setAccessToken(httpRequest.getHeader("accessToken"));
		tokenInVo.setRefreshToken(httpRequest.getHeader("refreshToken"));
		tokenInVo.setUserId(httpRequest.getHeader("userId"));
		
		userService.checkUser(tokenInVo);

		boardService.deleteBoardDetail(boardInVo);
			
		logger.info("============= deleteBoardDetail controller end ================");
		logger.info("================================================================");
//		return boardOutVo;
	}
	
}
