package com.sih.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sih.controller.vo.DetailOutVo;
import com.sih.controller.vo.NewsInqInVo;
import com.sih.controller.vo.NewsOutVo;
import com.sih.controller.vo.WordDetailOutVo;
import com.sih.controller.vo.WordInVo;
import com.sih.controller.vo.WordOutVo;
import com.sih.dao.SihDao;
import com.sih.dao.dto.DetailDto;
import com.sih.dao.dto.NewsDto;
import com.sih.dao.dto.NewsInqDto;
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
	public WordDetailOutVo selectDetailList(WordInVo wordInVo){
		
		logger.info("==========================================================");
		logger.info("============ selectDetailList service start ============");
		
		WordDto wordsInDto = new WordDto();
		
		wordsInDto.setWordId(wordInVo.getWordId());
		
		WordDto wordDto = sihDao.selectWord(wordsInDto);
		
		WordDetailOutVo outVo= new WordDetailOutVo();
		
		String inqCndtCon = wordInVo.getInqCndtCon();
		
		List<DetailOutVo> detailOutVoList = new ArrayList<DetailOutVo>();
		
		if("".equals(inqCndtCon) || inqCndtCon == null) {
			
			List<DetailDto> detailDtoList = sihDao.selectDetailList(wordsInDto);
			
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
			
			outVo.setDetailOutVoList(detailOutVoList);
			outVo.setWordContent(wordDto.getWordContent());
			outVo.setWordId(wordDto.getWordId());
		}
		
		else {
			
			NewsInqDto newsInqDto = new NewsInqDto();
			
			newsInqDto.setInqCndtCon(inqCndtCon);
			
			List<DetailDto> detailDtoList = sihDao.selectListInqNews(newsInqDto);
			
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
			
			outVo.setDetailOutVoList(detailOutVoList);
			outVo.setWordContent(inqCndtCon);
			
		}
		
		
		return outVo;
	}
	
	/*
	 * 설명 : 메인 - 상세페이지에서 직접 검색
	 */
	@Transactional
	public List<DetailOutVo> selectListInqNews(NewsInqInVo newsInqInVo){
		
		NewsInqDto newsInqDto = new NewsInqDto();
		
		newsInqDto.setInqCndtCon(newsInqInVo.getInqCndtCon());
		
		List<DetailDto> detailDtoList = sihDao.selectListInqNews(newsInqDto);
		
		List<DetailOutVo> detailOutVoList = new ArrayList<DetailOutVo>();
		
		for (DetailDto detail : detailDtoList) {
			DetailOutVo detailOutVo = new DetailOutVo();
			
			detailOutVo.setNewsTitle(detail.getNewsTitle());
			detailOutVo.setNewsId(detail.getNewsId());
			detailOutVo.setNewsUrl(detail.getNewsUrl());
			
			detailOutVoList.add(detailOutVo);
		}
		return detailOutVoList;
	}

}
