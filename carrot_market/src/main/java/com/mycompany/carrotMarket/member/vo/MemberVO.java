package com.mycompany.carrotMarket.member.vo;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class MemberVO {
	private String authority;

	private String id;

	private String pw;

	private String name;

	private String email;

	private String nickname;

	private String region1;

	private String region2;

	private float manner;

	private String fileName;

	private MultipartFile profile_image;

	private Date created_at;

	public MemberVO() {
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getRegion1() {
		return region1;
	}

	public void setRegion1(String region1) {
		this.region1 = region1;
	}

	public String getRegion2() {
		return region2;
	}

	public void setRegion2(String region2) {
		this.region2 = region2;
	}

	public float getManner() {
		return manner;
	}

	public void setManner(float manner) {
		this.manner = manner;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public MultipartFile getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(MultipartFile profile_image) {
		this.profile_image = profile_image;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	@Override
	public String toString() {
		return "Member{" + "id='" + id + '\'' + ", pw='" + pw + '\'' + ", email='" + email + '\'' + '}';
	}

}
