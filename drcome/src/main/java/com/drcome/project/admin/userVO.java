package com.drcome.project.admin;

import java.util.Date;

import lombok.Data;

@Data
public class userVO {
	
	String userId;
	String userPw;
	String userName;
	String phone;
	Date birthday;
	String gender;
	String identification;
	String userStatus;
	Date joindate;
}
