package com.sih.dao.dto;

import java.math.BigInteger;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AtchDtlDto {
	
	private BigInteger atchNo; 
	
	private BigInteger atchDtlno;
	
	private String atchNm;
	
	private long atchSize;
	
	private String atchPhysNm;
	
}