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
import com.sih.controller.vo.TokenInVo;
import com.sih.controller.vo.TokenOutVo;
import com.sih.controller.vo.UserInVo;
import com.sih.controller.vo.UserOutVo;
import com.sih.controller.vo.WordInVo;
import com.sih.controller.vo.WordOutVo;
import com.sih.dao.dto.UserDto;
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
	@PostMapping("/signup")
	public UserDto signin(@RequestBody UserInVo userInVo){
		logger.info("=================================================");
		logger.info("============ signin Controller start ============");
		
		if (userInVo == null) {
			throw new RuntimeException("selectDetailList Controller 입력조건");
		}
		
		UserDto result = userService.signup(userInVo);
		
		logger.info("============ signin Controller end ============");
		logger.info("===============================================");
		return result;
	}
	
	/*
	 * 설명 : 회원정보 수정 컨트롤러
	 */
	@PostMapping("/mdfc")
	public UserDto mdfcUserInfo(@RequestBody UserInVo userInVo){
		logger.info("=================================================");
		logger.info("============ mdfcUserInfo Controller start ============");
		
		if (userInVo == null) {
			throw new RuntimeException("selectDetailList Controller 입력조건");
		}
		
		UserDto result = userService.mdfcUserInfo(userInVo);
		
		logger.info("============ mdfcUserInfo Controller end ============");
		logger.info("===============================================");
		return result;
	}
	
	/*
	 * 설명 : 유저정보 조회 컨트롤러
	 */
	@PostMapping("/selectUser")
	public UserOutVo selectUser(@RequestBody UserInVo userInVo){
		logger.info("======================================================");
		logger.info("============ selectUser Controller start ============");
		
		if (userInVo == null) {
			throw new RuntimeException("selectUser Controller 입력조건");
		}
		
		UserOutVo result = userService.selectUser(userInVo);
		
		logger.info("============ selectUser Controller end ============");
		logger.info("=====================================================");
		return result;
	}
	
	/*
	 * 설명 : 유저정보 조회 컨트롤러
	 */
	@PostMapping("/checkUser")
	public TokenOutVo checkUser(@RequestBody TokenInVo tokenInVo){
		logger.info("======================================================");
		logger.info("============ checkUser Controller start ============");
		
		if (tokenInVo == null) {
			throw new RuntimeException("checkUser Controller 입력조건");
		}
		
		TokenOutVo result = userService.checkUser(tokenInVo);
		
		
		
		logger.info("============ checkUser Controller end ============");
		logger.info("=====================================================");
		return result;
	}
	
	
}
