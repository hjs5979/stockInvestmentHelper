package com.sih.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sih.controller.vo.DetailOutVo;
import com.sih.controller.vo.NewsOutVo;
import com.sih.controller.vo.WordInVo;
import com.sih.controller.vo.WordOutVo;
import com.sih.dao.SihDao;
import com.sih.dao.dto.DetailDto;
import com.sih.dao.dto.NewsDto;
import com.sih.dao.dto.WordDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DetailService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final SihDao sihDao;
	
	/*
	 * 설명 : 단어 클릭 시 상세 조회 서비스
	 */
	@Transactional
	public List<DetailOutVo> selectDetailList(WordInVo wordInVo){
		
		WordDto wordsInDto = new WordDto();
		
		wordsInDto.setWordId(wordInVo.getWordId());
		
		List<DetailDto> detailDtoList = sihDao.selectDetailList(wordsInDto);
		
		List<DetailOutVo> detailOutVoList = new ArrayList<DetailOutVo>();
		
		for (DetailDto detail : detailDtoList) {
			DetailOutVo detailOutVo = new DetailOutVo();
			
			detailOutVo.setWordId(detail.getWordId());
			detailOutVo.setWordCount(detail.getWordCount());
			detailOutVo.setWordContent(detail.getWordContent());
			detailOutVo.setNewsTitle(detail.getNewsTitle());
			detailOutVo.setNewsId(detail.getNewsId());
			detailOutVo.setNewsUrl(detail.getNewsUrl());
			
			detailOutVoList.add(detailOutVo);
		}
		return detailOutVoList;
	}
}
