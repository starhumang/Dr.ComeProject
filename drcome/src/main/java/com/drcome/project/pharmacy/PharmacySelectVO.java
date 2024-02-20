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
	String userId;
	
	//처방전 가져오기
	private String medicineName;
	private int dosage;
	private int doseCnt;
	private int doseDay;
	private Integer clinicNo;
	
	// 선택 목록 확인
	private String pharmacyName;
	private String pharmacyPhone;
	private String printStatus;
	private String selectPharmacyid;
}
