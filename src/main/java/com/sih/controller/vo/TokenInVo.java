package com.sih.controller.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenInVo {
	
	private String accessToken;
	
	private String refreshToken;
	
	private String userId;
	
	private String userRole;

}
