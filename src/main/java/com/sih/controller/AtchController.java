package com.sih.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import com.sih.controller.vo.AtchInVo;
import com.sih.controller.vo.BoardInqInVo;
import com.sih.controller.vo.PageOutVo;
import com.sih.dao.dto.AtchDto;
import com.sih.service.AtchService;
import com.sih.service.BoardService;
import com.sih.service.CustomException;
import com.sih.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/atch")
public class AtchController {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private final AtchService atchService;
	
	private final MessageSource messageSource;
	/*
	 * 설명 : 다운로드 컨트롤러
	 */
	@GetMapping("/dnld")
	public void dnld(HttpServletResponse response ,@RequestParam BigInteger atchNo, @RequestParam BigInteger atchDtlno) throws IOException, CustomException {
		logger.info("===========================================================");
		logger.info("============ dnld controller start =============");
		
//		if (atchInVo == null) {
//			throw new RuntimeException("dnld Controller 입력조건");
//		}
		
		AtchDto atchOutDto = atchService.dnld(atchNo, atchDtlno);
		
		String directory = atchOutDto.getAtchPath();
		
		String path = directory + "/" + atchOutDto.getAtchPhysNm();
		
		byte[] fileByte;
		
		try {
			fileByte = FileUtils.readFileToByteArray(new File(path));
		}
		catch(Exception e) {
			throw new CustomException(messageSource.getMessage("ATCH001", null, LocaleContextHolder.getLocale()));
		}
//		Resource resource = new FileSystemResource(filePath);
//		
		String fileName = atchOutDto.getAtchNm();
		String encodedFileName = URLEncoder.encode(fileName, "UTF-8");
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; fileName=\"" + encodedFileName +"\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();		
		
		logger.info("============ dnld controller end ============");
		logger.info("========================================================");
		
		
	}

}
