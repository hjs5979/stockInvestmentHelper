package com.sih.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sih.controller.vo.StockInVo;
import com.sih.controller.vo.StockOutVo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/test")
public class TestController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping("/test")
	public String testMethod() {
		
		logger.info("test start");
		
		return "test";
	}
	
	

}
