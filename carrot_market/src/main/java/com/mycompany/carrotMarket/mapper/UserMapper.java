package com.mycompany.carrotMarket.mapper;

import org.springframework.security.core.userdetails.User;

public interface UserMapper {
	User findById(String id);
}
