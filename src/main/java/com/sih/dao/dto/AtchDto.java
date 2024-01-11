package com.sih.dao.dto;

import java.math.BigInteger;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AtchDto {
	
	private String atchPath;
	
	private BigInteger atchNo; 
	
	private BigInteger atchDtlno;
	
	private String atchNm;
	
	private long atchSize;
	
	private String atchPhysNm;
	
	private long atchTtlSize;
	
	private long atchCnt;
	
}
