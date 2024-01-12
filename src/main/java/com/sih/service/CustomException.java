package com.sih.service;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.sih.dao.SihDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomException extends Exception {

	public CustomException(String message) {
	    
		super(message);
		
	}

};