package com.sih.dao.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class WordDto {
	
	private Integer wordId;
	
	private String wordContent;
		
	private Integer wordCount;
}
