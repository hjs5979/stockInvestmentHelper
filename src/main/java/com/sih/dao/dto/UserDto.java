package com.sih.dao.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
	private String userId;
	
	private String userPassword;
	
	private String userName;
	
	private String userEmail;
	
	private String userRole;
}
