package com.sih.controller.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenOutVo {
	
	private String accessToken;
	
	private String refreshToken;

}
