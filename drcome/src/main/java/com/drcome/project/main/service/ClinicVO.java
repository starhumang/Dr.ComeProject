package com.drcome.project.main.service;

import java.util.Date;

import lombok.Data;

@Data
public class ClinicVO {
	private Integer clinicNo;
	private String clinicSymptom; //증상
	private String specificity; //특이사항
	private char payYN; //급여, 비급여 여부
	private int paymentNo; //결제번호
	private int reserveNo; //예약번호
	private char perscriptionYN; //처방전 유무
	private Date clinicDate;// 진료일자
	private String hospitalId; //병원아이디
	private int patientNo; //환자번호
}
