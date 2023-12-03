package com.sih.dao.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetailDto {
	private Integer newsId;
	
	private String newsTitle;
	
	private String newsUrl;
	
	private Integer wordId;
	
	private String wordContent;
		
	private Integer wordCount;
}
