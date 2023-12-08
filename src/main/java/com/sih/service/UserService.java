package com.sih.service;

import java.security.Key;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.sih.controller.vo.UserInVo;
import com.sih.controller.vo.UserOutVo;

import com.sih.dao.UserDao;
import com.sih.dao.dto.UserDto;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserDao userDao;
	
	private final long expiration = 3600 * 1000;
	private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*
	 * 설명 : 로그인 서비스
	 */
	public ResponseEntity<?> login(UserInVo userInVo, HttpServletResponse httpResponse){
		
		logger.info("=========================================================");
		logger.info("================== login service start ==================");
		
		if(ObjectUtils.isEmpty(userInVo.getUserId())) {
			throw new RuntimeException("login service 입력조건 [ userId ]");
		}
		if(ObjectUtils.isEmpty(userInVo.getUserPassword())) {
			throw new RuntimeException("login service 입력조건 [ userPassword ]");
		}
		
		UserDto userInDto = new UserDto();
		
		userInDto.setUserId(userInVo.getUserId());
		userInDto.setUserPassword(userInVo.getUserPassword());
		
		UserDto userOutDto = userDao.login(userInDto);
		
		UserOutVo userOutVo = new UserOutVo();
		
		if (userOutDto == null) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		userOutVo.setUserId(userOutDto.getUserId());
		userOutVo.setUserPassword(userOutDto.getUserPassword());
		userOutVo.setUserName(userOutDto.getUserName());
		userOutVo.setUserEmail(userOutDto.getUserEmail());
		
//		String token = this.generateToken(userOutDto.getUserId());
		Cookie cookie = new Cookie("userId", String.valueOf(userInVo.getUserId()));
	    cookie.setMaxAge(60 * 60);  // 쿠키 유효 시간 : 1시간
	    cookie.setPath("/");
	    cookie.setDomain("localhost");
	    cookie.setSecure(false);
	    cookie.setHttpOnly(false);
	    
	    httpResponse.addCookie(cookie);
		
		logger.info("selectWordList service end");
		
		return new ResponseEntity<>(userOutVo, HttpStatus.OK);
	}
	
	/*
	 * 설명 : 토큰 생성 (미완)
	 */
	public String generateToken(String userId) {
		Date now = new Date();
		
		Date expireDate = new Date(now.getTime() + expiration);
		
		return Jwts.builder()
				.setSubject(userId)
				.setIssuedAt(now)
				.setExpiration(expireDate)
				.signWith(key)
				.compact();
	}
	
	/*
	 * 설명 : 아이디 중복 체크 서비스
	 */
	public Integer idcheck(UserInVo userInVo){
			
		    logger.info("============================================================");
			logger.info("================== idcheck service start ===================");
			
			if(ObjectUtils.isEmpty(userInVo.getUserId())) {
				throw new RuntimeException("idcheck service 입력조건 [ userId ]");
			}
			
			UserDto userInDto = new UserDto();
			
			userInDto.setUserId(userInVo.getUserId());
			
			Integer checkYn = userDao.idcheck(userInDto);
			
			logger.info("============== selectWordList service end =================");
			logger.info("===========================================================");
			return checkYn;
		}
	
	/*
	 * 설명 : 회원가입 서비스
	 */
	public void signin(UserInVo userInVo){
		
		logger.info("============================================================");
		logger.info("================= signin service start =====================");
		
		this.vrfcSigninInputValue(userInVo);
		
		UserDto userInDto = new UserDto();
		
		userInDto.setUserId(userInVo.getUserId());
		userInDto.setUserPassword(userInVo.getUserPassword());
		userInDto.setUserName(userInVo.getUserName());
		userInDto.setUserEmail(userInVo.getUserEmail());
		
		Integer checkYn = userDao.idcheck(userInDto);
		
		if(checkYn > 0) {
			throw new RuntimeException("signin : 아이디 중복");
		}
		
		userDao.signin(userInDto);
		
		logger.info("============= selectWordList service end ===================");
		logger.info("============================================================");
		
	}
	
	/*
	 * 설명 : 회원가입 입력값 체크 메서드
	 */
	private void vrfcSigninInputValue(UserInVo userInVo) {
		
		if(ObjectUtils.isEmpty(userInVo.getUserId())) {
			throw new RuntimeException("signin service 입력조건 [ userId ]");
		}
		if(ObjectUtils.isEmpty(userInVo.getUserPassword())) {
			throw new RuntimeException("signin service 입력조건 [ userPassword ]");
		}
		if(ObjectUtils.isEmpty(userInVo.getUserName())) {
			throw new RuntimeException("signin service 입력조건 [ userName ]");
		}
		if(ObjectUtils.isEmpty(userInVo.getUserEmail())) {
			throw new RuntimeException("signin service 입력조건 [ userEmail ]");
		}
		
	}
//	public boolean validateToken(String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//            return true;
//        } catch (SecurityException | MalformedJwtException e) {
//            logger.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
//        } catch (ExpiredJwtException e) {
//            logger.info("Expired JWT token, 만료된 JWT token 입니다.");
//        } catch (UnsupportedJwtException e) {
//            logger.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
//        } catch (IllegalArgumentException | SignatureException e) {
//            logger.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
//        }
//        return false;
//	}

}
