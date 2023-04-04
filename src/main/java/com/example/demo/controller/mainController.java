package com.example.demo.controller;

import com.example.demo.dto.responseDTO;
import com.example.demo.dto.joinDTO;
import com.example.demo.dto.mainDTO;
import com.example.demo.dto.newsDTO;
import com.example.demo.model.mainEntity;
import com.example.demo.model.newsEntity;
import com.example.demo.model.newswordsEntity;
import com.example.demo.service.joinService;
import com.example.demo.service.mainService;
import com.example.demo.dto.stockDTO;
import com.example.demo.model.stockEntity;
import com.example.demo.service.stockService;
//import com.example.demo.dto.joinDTO;
//import com.example.demo.model.joinEntity;
//import com.example.demo.service.joinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("main")
public class mainController {

	@Autowired
	private mainService service;
	
	@GetMapping("words")
	public ResponseEntity<?> retrieveList() {
//		String word = "대한";
		// (1) 서비스 메서드의 retrieve메서드를 사용해 Todo리스트를 가져온다
		List<mainEntity> entities = service.retrieve();

		// (2) 자바 스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환한다.
		List<mainDTO> dtos = entities.stream().map(mainDTO::new).collect(Collectors.toList());

		// (6) 변환된 TodoDTO리스트를 이용해ResponseDTO를 초기화한다.
		responseDTO<mainDTO> response = responseDTO.<mainDTO>builder().data(dtos).build();
		
		// (7) ResponseDTO를 리턴한다.
		return ResponseEntity.ok().body(response);
	}
	
	@Autowired
	private stockService service2;
	
	@GetMapping("stock")
	public ResponseEntity<?> retrieveList2() {
		
		List<stockEntity> entities2 = service2.retrieve();

		List<stockDTO> dtos2 = entities2.stream().map(stockDTO::new).collect(Collectors.toList());

		responseDTO<stockDTO> response2 = responseDTO.<stockDTO>builder().data(dtos2).build();
		
		return ResponseEntity.ok().body(response2);
	}
	
	@Autowired
	private joinService service3;
	
	@GetMapping("word")
	public ResponseEntity<?> retrieveList3(@RequestParam("wordid") Integer wordid) {
		
		List<newswordsEntity> entities3 = service3.retrieve(wordid);
		
		List<newsEntity> newsEntities = service3.queryNews(entities3);
		
		List<newsDTO> dtos3 = newsEntities.stream().map(newsDTO::new).collect(Collectors.toList());

		responseDTO<newsDTO> response3 = responseDTO.<newsDTO>builder().data(dtos3).build();
		
		return ResponseEntity.ok().body(response3);
	
	}
}