package com.sih.dao.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RedisDto {
	
	private String accessToken;
	
	private String userId;
}
