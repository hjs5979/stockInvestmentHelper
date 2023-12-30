package com.sih.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sih.dao.SihDao;
import com.sih.dao.UserDao;
import com.sih.dao.dto.UserDto;

import lombok.RequiredArgsConstructor;

//@RequiredArgsConstructor
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
	
//	private final UserDao userDao;
	
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 실제로는 데이터베이스에서 사용자 정보를 조회하여 반환
//        // 이 예제에서는 간단히 하드코딩
//    	UserDto userInDto = new UserDto();
//		
//		userInDto.setUserName(username);
//		UserDto userOutDto = userDao.login(userInDto);
//		
//		if (userOutDto == null){
//			throw new UsernameNotFoundException("유저 정보 없음");
//		}
//		
//		UserDetails user = User.withUsername(userOutDto.getUserName())
//	            .password(userOutDto.getUserPassword())
//	            .roles(userOutDto.getUserRole())
//	            .build();
//		
//        return user;
//    }
//}
