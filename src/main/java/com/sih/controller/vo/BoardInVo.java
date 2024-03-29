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
public class BoardInVo {
	private BigInteger boardId;
	
	private String boardTitle;
	
	private LocalDate boardYmd;
	
	private String boardWrtId;
	
	private String boardContent;
	
	private List<MultipartFile> fileList;
	
	private BigInteger atchNo;
	
	private List<AtchDto> cancelFiles;
	
	private long atchCnt;
	
	private long atchTtlSize;
}
