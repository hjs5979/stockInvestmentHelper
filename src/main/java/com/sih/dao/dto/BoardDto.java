package com.sih.dao.dto;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {
	private BigInteger boardId;
	
	private String boardTitle;
	
	private LocalDateTime boardCretTs;
	
	private String boardWrtId;
	
	private String boardContent;
	
	private BigInteger atchNo;
	
	private Integer atchYn;
}
