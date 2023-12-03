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
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserInVo userInVo, HttpServletResponse httpResponse){
		
		logger.info("login service start");
			
		ResponseEntity<?> responseEntity = userService.login(userInVo, httpResponse);
		
		logger.info("login service end");
		
		return responseEntity;
	}
	
	@PostMapping("/idcheck")
	public Integer idcheck(@RequestBody UserInVo userInVo){
		
		logger.info("login service start");
			
		Integer checkYn = userService.idcheck(userInVo);
		
		logger.info("login service end");
		
		return checkYn;
	}
	
	@PostMapping("/signin")
	public Integer signin(@RequestBody UserInVo userInVo){
		
		logger.info("login service start");
			
		userService.signin(userInVo);
		
		logger.info("login service end");
		
		return 1;
	}
	
}
