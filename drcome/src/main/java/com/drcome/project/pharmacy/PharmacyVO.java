package com.drcome.project.pharmacy;

import java.util.Date;

import lombok.Data;

@Data
public class PharmacyVO {

	  private String pharmacyId;
	  private String pharmacyPw;
	  private String pharmacyName;
	  private String address;
	  private String pharmacyPhone;
	  private String pharmacyFax;
	  private String pharmacyImg;
	  private String ceoName;
	  private String ceoPhone;
	  private String ceoLicense;
	  private String pharmacyStatus;
	  private String holiday;
	  private String opentime;
	  private String closetime;
	  private Date joindate;
	}