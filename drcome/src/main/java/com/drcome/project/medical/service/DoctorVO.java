package com.drcome.project.medical.service;

import java.util.List;

import lombok.Data;

@Data
public class DoctorVO {
	private Integer doctorNo;
	private String subject;
	private String doctorName;
	private String doctorLicense;
	private String holiday;
	private String hospitalId;
	private Integer doctorTimeno;
	
	List<DoctorTimeVO> times;
}
