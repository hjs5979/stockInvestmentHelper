package com.sih.controller.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserInVo {
	private String userId;
	
	private String userPassword;
	
	private String userName;
	
	private String userEmail;
	
	private String newPassword;
	
	private String userRole;
}
