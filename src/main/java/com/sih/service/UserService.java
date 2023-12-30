package com.sih.service;

import java.security.Key;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.sih.config.RedisConfig;
import com.sih.controller.vo.TokenInVo;
import com.sih.controller.vo.TokenOutVo;
import com.sih.controller.vo.UserInVo;
import com.sih.controller.vo.UserOutVo;

import com.sih.dao.UserDao;
import com.sih.dao.dto.RedisDto;
import com.sih.dao.dto.UserDto;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserDao userDao;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	private final BCryptPasswordEncoder passwordEncoder;
	private final String headerJwt = "authorization";
	private final MessageSource messageSource;
	
	private final RedisTemplate<String,String> redisTemplate;
	
	private final RedisConfig redisconfig;
	
	/*
	 * 설명 : 로그인 서비스
	 */
	@Transactional
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
		UserDto userOutDto = userDao.selectUser(userInDto);
		
		if (passwordEncoder.matches(userInVo.getUserPassword(), userOutDto.getUserPassword())){
			logger.info("======================= 유저 확인 성공 =============================");
		}
		else {
			logger.info("======================= 유저 확인 실패 =============================");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
//		UserOutVo userOutVo = new UserOutVo();
//		
//		userOutVo.setUserId(userOutDto.getUserId());
//		userOutVo.setUserName(userOutDto.getUserName());
//		userOutVo.setUserEmail(userOutDto.getUserEmail());
		
		TokenOutVo tokenOutVo = new TokenOutVo();
		
		String accessToken = this.generateToken(userOutDto.getUserId());
		String refreshToken = this.createRefreshToken();
		
		tokenOutVo.setAccessToken(accessToken);
		tokenOutVo.setRefreshToken(refreshToken);
		
		RedisDto redisDto = new RedisDto();
//		Cookie cookie = new Cookie("userId", String.valueOf(userInVo.getUserId()));
//	    cookie.setMaxAge(60 * 60);  // 쿠키 유효 시간 : 1시간
//	    cookie.setPath("/");
//	    cookie.setDomain("localhost");
//	    cookie.setSecure(false);
//	    cookie.setHttpOnly(false);
	    
//	    httpResponse.addCookie(cookie);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("accessToken", accessToken);
		map.put("userId", userInVo.getUserId());
		
		redisTemplate.opsForHash().putAll(refreshToken, map);
		
//		redisTemplate.opsForValue().set(refreshToken, redisDto.toString(), 864000000);
		
		logger.info("login service service end");
		
		return new ResponseEntity<>(tokenOutVo, HttpStatus.OK);
	}
	
	/*
	 * 설명 : access 토큰 생성 (미완)
	 */
	private String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 600000); // 10분 동안 유효

        return Jwts.builder()
        		.signWith(key)
        		.setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate) 
                .compact();
    }
	
	
	/*
	 * 설명 : refresh 토큰 생성 (미완)
	 */
	private String createRefreshToken() {
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
//	private String createRefreshToken() {
//        Date now = new Date();
//        return Jwts.builder()
//                .setIssuedAt(now)
//                .setExpiration(new Date(now.getTime() + 864000000))
//                .signWith(key)
//                .compact();
//    }
	
	/*
	 * 설명 : 아이디 중복 체크 서비스
	 */
	@Transactional
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
	@Transactional
	public UserDto signup(UserInVo userInVo){
		
		logger.info("============================================================");
		logger.info("================= signin service start =====================");
		
		this.vrfcSigninInputValue(userInVo);
		
		UserDto userInDto = new UserDto();
				
		userInDto.setUserId(userInVo.getUserId());
		userInDto.setUserPassword(passwordEncoder.encode(userInVo.getUserPassword()));
//		userInDto.setUserPassword(userInVo.getUserPassword());
		userInDto.setUserName(userInVo.getUserName());
		userInDto.setUserEmail(userInVo.getUserEmail());
		userInDto.setUserRole("C0MMON");
		
		Integer checkYn = userDao.idcheck(userInDto);
		
		if(checkYn > 0) {
			throw new RuntimeException("signin : 아이디 중복");
		}
		
		userDao.signin(userInDto);
		
		logger.info("============= signin service end ===================");
		logger.info("============================================================");
		return userInDto;
		
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
	/*
	 * 설명 : 회원정보 수정 서비스
	 */
	@Transactional
	public UserDto mdfcUserInfo(UserInVo userInVo){
		
		logger.info("============================================================");
		logger.info("================= mdfcUserInfo service start =====================");
		
		this.vrfcMdfcUserInfoValue(userInVo);
		
		UserDto userInDto = new UserDto();
		
		userInDto.setUserId(userInVo.getUserId());
		
		UserDto userOutDto = userDao.selectUser(userInDto);
		
		if (passwordEncoder.matches(userInVo.getUserPassword(), userOutDto.getUserPassword())){
			logger.info("======================= 로그인 성공 =============================");
		}
		else {
			logger.info("======================= 로그인 실패 =============================");
			throw new RuntimeException("mdfcUserInfo : 비밀번호 오류");
		}
		
		userOutDto.setUserEmail(userInVo.getUserEmail());
		
		userDao.mdfcUserInfo(userOutDto);
		
		logger.info("============= mdfcUserInfo service end ===================");
		logger.info("============================================================");
		
		return userOutDto;
		
	}
	
	private void vrfcMdfcUserInfoValue(UserInVo userInVo) {
		
		if(ObjectUtils.isEmpty(userInVo.getUserId())) {
			throw new RuntimeException("MdfcUserInfo service 입력조건 [ userId ]");
		}
		if(ObjectUtils.isEmpty(userInVo.getUserPassword())) {
			throw new RuntimeException("MdfcUserInfo service 입력조건 [ userPassword ]");
		}
		if(ObjectUtils.isEmpty(userInVo.getUserEmail())) {
			throw new RuntimeException("MdfcUserInfo service 입력조건 [ userEmail ]");
		}
		
	}
	
	/*
	 * 설명 : 비밀번호 수정 서비스
	 */
	@Transactional
	public UserDto mdfcPassword(UserInVo userInVo){
		
		logger.info("============================================================");
		logger.info("================= mdfcPassword service start =====================");
		
		if(ObjectUtils.isEmpty(userInVo.getUserPassword())) {
			throw new RuntimeException("mdfcPassword service 입력조건 [ userPassword ]");
		}
		if(ObjectUtils.isEmpty(userInVo.getNewPassword())) {
			throw new RuntimeException("mdfcPassword service 입력조건 [ newPassword ]");
		}
		
		UserDto userInDto = new UserDto();
		
		userInDto.setUserId(userInVo.getUserId());
		
		UserDto userOutDto = userDao.selectUser(userInDto);
		
		if (passwordEncoder.matches(userInVo.getUserPassword(), userOutDto.getUserPassword())){
			logger.info("======================= 로그인 성공 =============================");
		}
		else {
			logger.info("======================= 로그인 실패 =============================");
			throw new RuntimeException("mdfcPassword : 비밀번호 오류");
		}
		
		userOutDto.setUserPassword(userInVo.getNewPassword());
		
		userDao.mdfcUserInfo(userOutDto);
		
		logger.info("============= mdfcPassword service end ===================");
		logger.info("============================================================");
		
		return userOutDto;
		
	}
	
	/*
	 * 설명 : 유저정보 조회 서비스
	 */
	@Transactional
	public UserOutVo selectUser(UserInVo userInVo){
		
		logger.info("=========================================================");
		logger.info("================== selectUser service start ==================");
		
		if(ObjectUtils.isEmpty(userInVo.getUserId())) {
			throw new RuntimeException("login service 입력조건 [ userId ]");
		}
		
		UserDto userInDto = new UserDto();
		
		userInDto.setUserId(userInVo.getUserId());
		UserDto userOutDto = userDao.selectUser(userInDto);
		
		
		UserOutVo userOutVo = new UserOutVo();
//		
		userOutVo.setUserId(userOutDto.getUserId());
		userOutVo.setUserName(userOutDto.getUserName());
		userOutVo.setUserEmail(userOutDto.getUserEmail());
		
		logger.info("selectUser service service end");
		
		return userOutVo;
	}
	
	/*
	 * 설명 : 유저토큰 확인 서비스
	 */
	@Transactional
	public TokenOutVo checkUser(TokenInVo tokenInVo) {
		
		logger.info("=============================================================");
		logger.info("================== checkUser service start ==================");
		
		String redisAccessToken = redisTemplate.opsForHash().get(tokenInVo.getRefreshToken(), "accessToken")== null ? "" : redisTemplate.opsForHash().get(tokenInVo.getRefreshToken(), "accessToken").toString();
    	String redisUserId = redisTemplate.opsForHash().get(tokenInVo.getRefreshToken(), "userId")==null ? "" : redisTemplate.opsForHash().get(tokenInVo.getRefreshToken(), "userId").toString();
    	
    	TokenOutVo result = new TokenOutVo();
		
		try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(tokenInVo.getAccessToken());
      
        } catch (Exception e) {
        	
        	
        	if (ObjectUtils.isEmpty(redisAccessToken) || ObjectUtils.isEmpty(redisUserId)) {
        		throw new RuntimeException("유효하지 않음1");
        	}
        	
        	else if(redisUserId.equals(tokenInVo.getUserId())){
       
        		String accessToken = this.generateToken(tokenInVo.getUserId());
        		
        		redisTemplate.opsForHash().put(tokenInVo.getRefreshToken(), "accessToken", accessToken);
        		
        		result.setAccessToken(accessToken);
        		result.setRefreshToken(tokenInVo.getRefreshToken());
        		
        		return result;
        	}
        	else {
        		throw new RuntimeException("유효하지 않음2");
        	}
        	
        	
        }
		
		result.setAccessToken(tokenInVo.getAccessToken());
		result.setRefreshToken(tokenInVo.getRefreshToken());
		
		logger.info("=========================================================");
		logger.info("================== checkUser service end ==================");
		
		return result;
		
	}
	
//	private void validateToken(String token) {
//        try {
//            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
//      
//        } catch (SecurityException | MalformedJwtException e) {
//            logger.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
//        } catch (ExpiredJwtException e) {
//            logger.info("Expired JWT token, 만료된 JWT token 입니다.");
//        } catch (UnsupportedJwtException e) {
//            logger.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
//        } catch (IllegalArgumentException e) {
//            logger.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
//        }
//	}
	

}
