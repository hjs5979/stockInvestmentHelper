package com.sih.controller.vo;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.sih.dao.dto.AtchDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardOutVo {
	private BigInteger boardId;
	
	private String boardTitle;
	
	private LocalDateTime boardCretTs;
	
	private String boardWrtId;
	
	private String boardContent;
	
	private BigInteger atchNo;
	
	private Integer atchYn;
	
	private List<AtchDto> fileList;
	
	private long atchTtlSize;
	
	private long atchCnt;
}
