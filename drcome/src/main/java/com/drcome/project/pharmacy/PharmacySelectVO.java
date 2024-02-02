package com.drcome.project.pharmacy;

import lombok.Data;

@Data
public class PharmacySelectVO {

	Integer pharmacySelectno;
	Integer perscriptionNo;
	String pharmacyId;
	String produceStatus;
	String rejection;
	String sendStatus;
	
	//처방전 가져오기
	private String medicineName;
	private int dosage;
	private int doseCnt;
	private int doseDay;
	private Integer clinicNo;
}
