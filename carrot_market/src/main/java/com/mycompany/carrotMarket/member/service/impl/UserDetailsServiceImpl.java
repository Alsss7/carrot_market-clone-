package com.mycompany.carrotMarket.member.service.impl;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mycompany.carrotMarket.mapper.UserMapper;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private final UserMapper userMapper;

	@Autowired
	public UserDetailsServiceImpl(SqlSessionTemplate sqlSessionTemplate) {
		this.userMapper = sqlSessionTemplate.getMapper(UserMapper.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userMapper.findById(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return User.builder()
				.username(user.getUsername())
				.password(user.getPassword())
				.authorities(user.getAuthorities())
				.build();
	}

}
