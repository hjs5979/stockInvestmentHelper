package com.sih.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.sih.controller.vo.AtchInVo;
import com.sih.controller.vo.BoardInVo;
import com.sih.dao.SihDao;
import com.sih.dao.dto.AtchDtlDto;
import com.sih.dao.dto.AtchDto;

import io.netty.util.internal.ObjectUtil;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AtchService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final SihDao sihDao;
	
	@Value("${sih.uploadPath}")
    private String uploadPath;
	
	/*
	 * 설명 : 업로드 서비스
	 */
	@Transactional
	public BigInteger upload(AtchInVo atchInVo) throws IOException{
		
		logger.info("===================================================================");
		logger.info("================== upload service start ==================");
		
//		this.vrfcUploadInputValue(atchInVo);
		
		String atchDirNm = LocalDate.now().toString();
		
		String folderPath = uploadPath + "/" + atchDirNm;
		
		File folder = new File(folderPath);
		
		if(!folder.exists()) {
			folder.mkdirs();
		}
		
		List<MultipartFile> fileList = atchInVo.getFileList(); 
		
		AtchDto atchDto = new AtchDto();
		
		atchDto.setAtchPath(folderPath);
		atchDto.setAtchCnt(atchInVo.getAtchCnt());
		atchDto.setAtchTtlSize(atchInVo.getAtchTtlSize());
		
		BigInteger atchNo;
		
		if(atchInVo.getAtchNo() == null) {
		
			sihDao.insertAtchBase(atchDto);
		
			atchNo = sihDao.lastInsertId();
		
		} else {
			atchNo = atchInVo.getAtchNo();
			
			AtchDto atchOutDto = sihDao.selectForUpdateAtchBase(atchNo);
			
			atchOutDto.setAtchCnt(atchDto.getAtchCnt());
			atchOutDto.setAtchTtlSize(atchDto.getAtchTtlSize());
			
			sihDao.updateAtchBase(atchOutDto);
						
		}
		if(atchInVo.getFileList() != null) {
			for (MultipartFile file : fileList) {
				
				AtchDtlDto atchDtlDto = new AtchDtlDto();
				
				String extension = "";
				
				String originalFilename = file.getOriginalFilename();
				
				int dotIndex = originalFilename.lastIndexOf(".");
				
				if (dotIndex > 0) { // 파일 이름에 점(.)이 존재하는 경우
		            extension = originalFilename.substring(dotIndex);
		        }
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
				
				// LocalDateTime 객체를 형식에 맞게 포맷팅
		        String formattedDateTime = LocalDateTime.now().format(formatter);
		        
		        BigInteger atchDtlno = sihDao.selectNmbnAtchDtlno(atchNo);
		        
				String atchNm = formattedDateTime + "-" + atchDtlno + extension;
				
				atchDtlDto.setAtchDtlno(atchDtlno);
				atchDtlDto.setAtchNo(atchNo);
				atchDtlDto.setAtchNm(originalFilename);
				atchDtlDto.setAtchPhysNm(atchNm);
				atchDtlDto.setAtchSize(file.getSize());
				
				sihDao.insertAtchDtl(atchDtlDto);
				
	//			atchDtlno = atchDtlno.add(BigInteger.valueOf(1));
				
				File outputFile = new File(folderPath + "/" +atchNm);
		        
				file.transferTo(outputFile);
		        
			}
		}
		
		logger.info("============ upload service end ==============");
		logger.info("=========================================================");
		return atchNo;
		
	}
	
	/*
	 * 설명 : 업로드 입력값 체크 메서드
	 */
	private void vrfcUploadInputValue(AtchInVo atchInVo) {
		
		List<MultipartFile> fileList = atchInVo.getFileList();
		
		if(fileList.size() < 1 || fileList.size() >= 3 ) {
			throw new RuntimeException("upload service 입력조건 [ files 개수 ]");
		}
		
		int fileTtlSize = 0;
		
		for (MultipartFile file : fileList) {
			
			if (file.getSize() > 10485760) {
				throw new RuntimeException("upload service 입력조건 [ file size ]");
			}
			
			fileTtlSize += file.getSize();
			
		}
	
		if(fileTtlSize >  10485760 * 3) {
			throw new RuntimeException("upload service 입력조건 [ file total size ]");
		}
	}
	
	/*
	 * 설명 : 다운로드 서비스
	 */
	@Transactional
	public AtchDto dnld(BigInteger atchNo, BigInteger atchDtlno) throws IOException{
		
		logger.info("=========================================================================");
		logger.info("=========================== dnld service start ==========================");
		
		AtchDto atchInDto = new AtchDto();
		atchInDto.setAtchNo(atchNo);
		atchInDto.setAtchDtlno(atchDtlno);
		
		AtchDto atchOutDto= sihDao.selectAtchDnld(atchInDto);
		
//		String directory = atchOutDto.getAtchPath();
//		
//		String path = directory + atchOutDto.getAtchPhysNm();
		
//		byte[] fileByte = FileUtils.readFileToByteArray(new File(path));
//		
//		Resource resource = new FileSystemResource(filePath);
//		
//		String fileName = atchOutDto.getAtchNm();
//		String encodedFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
//		if (atchInVo.getAtchNo() == null ) {
//			sihDao.insertAtchBase(null);
//		}
		
		logger.info("============================= dnld service end ============================");
		logger.info("===========================================================================");
		
		return atchOutDto;
	}
	
	/*
	 * 설명 : 업로드 삭제 서비스
	 */
	@Transactional
	public void deleteAtchDtl(List<AtchDto> cancelFiles) throws IOException{
		
		logger.info("=========================================================================");
		logger.info("=========================== deleteAtchDtl service start ==========================");
		
		for(AtchDto cancelFile : cancelFiles) {
			sihDao.deleteAtchDtl(cancelFile);
		}		
				
		logger.info("============================= deleteAtchDtl service end ============================");
		logger.info("===========================================================================");
		
	}
	
}
