package com.sih.controller.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetailOutVo {
	private Integer newsId;
	
	private String newsTitle;
	
	private String newsUrl;
	
	private Integer wordId;
	
	private String wordContent;
		
	private Integer wordCount;
}
