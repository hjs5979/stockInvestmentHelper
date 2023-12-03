package com.sih.dao.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardInqDto {
	private String inqCndtDcd;
	
	private String inqCndtCon;
	
	private String boardTitle;
	
	private String boardWrtId;
	
	private String boardContent;
}
