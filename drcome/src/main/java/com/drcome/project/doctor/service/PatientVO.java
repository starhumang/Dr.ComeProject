package com.drcome.project.doctor.service;

import java.util.Date;

import lombok.Data;

@Data
public class PatientVO {
	
	//기본정보
	private int reserveNo;
	private String userName;
	private String gender;
	private Date birthday;
	private String symptom;
	
	//진료이력
	private Integer clinicNo;
	private String clinicSymptom;
	private String specificity;
	private String perscriptionYn;
	private Date clinicDate;

	
	//처방전 가져오기
	private String medicineName;
	private int dosage;
	private int doseCnt;
	private int doseDay;


}
