package com.drcome.project.admin.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import lombok.Data;

@Entity
@Data
public class Hospital {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private String hospitalId;
	private String hospitalPw;
	private String hospitalName;
	private String address;
	private String phone;
	private String hospitalImg;
	private String mainSubject;
	private String directorName;
	private String directorPhone;
	private String directorLicense;
	private String hospitalStatus;
	private String holiday;
	private String opentime;
	private String closetime;
	private Date joindate;

	public void hospitalStatusUdpate() {
		this.hospitalStatus = "b2";
	}
}
