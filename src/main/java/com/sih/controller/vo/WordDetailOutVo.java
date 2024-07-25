package com.sih.controller.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WordDetailOutVo {
	private List<DetailOutVo> detailOutVoList;
	
	private Integer wordId;
	
	private String wordContent;

}
