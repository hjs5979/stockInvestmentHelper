package com.sih.dao.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewsDto {
	
	private Integer newsId;
	
	private String newsTitle;
	
	private String newsUrl;

}
