package com.example.demo.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.stockEntity;
import com.example.demo.persistence.stockRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class stockService {

	@Autowired
	private stockRepository repository;
	
	private void validate(final stockEntity entity) {
		if(entity == null) {
			log.warn("Entity cannot be null.");
			throw new RuntimeException("Entity cannot be null.");
		}

		if(entity.getId() == null) {
			log.warn("Unknown user.");
			throw new RuntimeException("Unknown user.");
		}
	}

	public List<stockEntity> retrieve(){
		return repository.findAll();
	}

}
