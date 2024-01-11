package com.sih.controller.vo;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.sih.dao.dto.AtchDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AtchInVo {
	
	private BigInteger atchNo; 
	
	private String atchDirNm;
	
	private List<MultipartFile> fileList;
	
	private BigInteger atchDtlno;
	
	private String atchPath;
	
	private String atchNm;
	
	private String atchPhysNm;
	
	private long atchCnt;
	
	private long atchTtlSize;
	
	private List<AtchDto> cancelfiles;
}
