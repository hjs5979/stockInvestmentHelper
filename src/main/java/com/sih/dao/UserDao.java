package com.sih.dao;

import org.apache.ibatis.annotations.Mapper;

import com.sih.dao.dto.UserDto;

@Mapper
public interface UserDao {
	public UserDto selectUser(UserDto inDto);
	
	public Integer idcheck(UserDto inDto);
	
	public void signin(UserDto inDto);
	
	public void mdfcUserInfo(UserDto inDto);
}
