package com.example.demo.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.newsEntity;
import com.example.demo.model.newswordsEntity;
import com.example.demo.persistence.joinRepository;
import com.example.demo.persistence.newsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class joinService {

	@Autowired
	private joinRepository repository;
	
	private void validate(final newswordsEntity entity) {
		if(entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null.");
		}

		if(entity.getId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}

	public List<newswordsEntity> retrieve(Integer wordsId){
		return repository.findByWordsId(wordsId);
	}
	
	@Autowired
	private newsRepository newsRepo;
	public List<newsEntity> queryNews(List<newswordsEntity> lne){
		List<newsEntity> ret = new ArrayList<>();
		
		
		for(int i=0;i<lne.size();i++) {
			List<newsEntity> newlne = newsRepo.findByIdIs(lne.get(i).getNewsId());
			ret.add(newlne.get(0));
		}
		
		return ret;
	}
	
}
