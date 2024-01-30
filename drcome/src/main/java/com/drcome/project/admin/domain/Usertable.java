package com.drcome.project.admin.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Usertable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String userId;
	private String userPw;
	private String userName;
	private String phone;
	private Date birthday;
	private String gender;
	private String identification;
	private String userStatus;
	private String grade;
	private Date joindate;
	
}
