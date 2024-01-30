package com.drcome.project.pharmacy;

import java.util.Date;

import lombok.Data;

@Data
public class PharmacyVO {

	String pharmacyId;
	String pharmacyPw;
	String pharmacyName;
	String address;
	String pharmacyPhone;
	String pharmacyFax;
	String pharmacyImg;
	String ceoName;
	String ceoPhone;
	String ceoLicense;
	String pharmacyStatus;
	String holiday;
	String opentime;
	String closetime;
	Date joindate;
}
