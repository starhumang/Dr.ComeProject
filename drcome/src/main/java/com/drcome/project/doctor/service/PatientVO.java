package com.drcome.project.doctor.service;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PatientVO {

	// 기본정보
	private int reserveNo;
	private String userName;
	private String gender;
	private Date birthday;
	private String symptom;

	// 진료
	private int clinicNo;
	private String clinicSymptom;
	private String specificity;
	private String payYn;
	private int paymentNo;
	// reserveNo는위에
	private String perscriptionYn;
	private Date clinicDate;
	private String hospitalId;
	private int patientNo;

	// 처방전
	private int perscriptionNo;
	private int dosage;
	private int doseCnt;
	private int doseDay;
	private Date perscriptionDate;
	private String medicineName;
	private int medicineNo;

	// 환자
	private String userId;
	private Date currentDate;

	// 초진 재진 여부
	private String visit;

	// 처방전리스트
	private List<PatientVO> perary;

	// 로우넘
	private int rn;

}
