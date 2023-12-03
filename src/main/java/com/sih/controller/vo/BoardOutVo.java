package com.sih.controller.vo;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardOutVo {
	private Integer boardId;
	
	private String boardTitle;
	
	private LocalDate boardYmd;
	
	private String boardWrtId;
	
	private String boardContent;
}
