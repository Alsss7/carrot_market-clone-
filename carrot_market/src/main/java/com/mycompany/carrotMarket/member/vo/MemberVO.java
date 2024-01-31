package com.mycompany.carrotMarket.member.vo;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component
public class MemberVO {
	private String id;
	private String pw;
	private String name;
	private String email;
	private String nickname;
	private String region1;
	private String region2;
	private float manner;
	private String profile_image;
	private Date created_at;

	public MemberVO() {

	}

	public MemberVO(String id, String pw, String name, String email, String nickname, String region1, String region2,
			String profile_image) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.nickname = nickname;
		this.region1 = region1;
		this.region2 = region2;
		this.profile_image = profile_image;
	}

	public String getId() {
		return id;
	}

	public String getPw() {
		return pw;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public String getNickname() {
		return nickname;
	}

	public String getRegion1() {
		return region1;
	}

	public String getRegion2() {
		return region2;
	}

	public float getManner() {
		return manner;
	}

	public String getProfile_image() {
		return profile_image;
	}

	public Date getCreated_at() {
		return created_at;
	}

}
