package com.sih.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sih.controller.vo.DetailOutVo;
import com.sih.controller.vo.StockOutVo;
import com.sih.controller.vo.UserInVo;
import com.sih.controller.vo.UserOutVo;
import com.sih.controller.vo.WordInVo;
import com.sih.controller.vo.WordOutVo;
import com.sih.service.DetailService;
import com.sih.service.MainService;
import com.sih.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final UserService userService;
	
	/*
	 * 설명 : 로그인 컨트롤러
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserInVo userInVo, HttpServletResponse httpResponse){

		logger.info("================================================");
		logger.info("============ login Controller start ============");
		
		if (userInVo == null) {
			throw new RuntimeException("selectDetailList Controller 입력조건 [ userInVo ] ");
		}
		
		ResponseEntity<?> responseEntity = userService.login(userInVo, httpResponse);
		
		logger.info("============ login Controller end ============");
		logger.info("==============================================");
		
		return responseEntity;
	}
	
	/*
	 * 설명 : 아이디중복체크 컨트롤러
	 */
	@PostMapping("/idcheck")
	public Integer idcheck(@RequestBody UserInVo userInVo){
		
		logger.info("==================================================");
		logger.info("============ idcheck Controller start ============");
		
		if (userInVo == null) {
			throw new RuntimeException("selectDetailList Controller 입력조건");
		}
		
		Integer checkYn = userService.idcheck(userInVo);
		
		logger.info("============ idcheck Controller end ============");
		logger.info("================================================");
		return checkYn;
	}
	
	/*
	 * 설명 : 회원가입 컨트롤러
	 */
	@PostMapping("/signin")
	public Integer signin(@RequestBody UserInVo userInVo){
		logger.info("=================================================");
		logger.info("============ signin Controller start ============");
		
		if (userInVo == null) {
			throw new RuntimeException("selectDetailList Controller 입력조건");
		}
		
		userService.signin(userInVo);
		
		logger.info("============ signin Controller end ============");
		logger.info("===============================================");
		return 1;
	}
	
}
