package com.sih.controller.vo;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageOutVo {
	private Integer countBoard;
	
	private List<BoardOutVo> content;
}
