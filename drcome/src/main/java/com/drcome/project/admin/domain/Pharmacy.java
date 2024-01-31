package com.drcome.project.admin.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Pharmacy {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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
	
	public void pharmacyStatusUdpate() {
			this.pharmacyStatus = "b2";
	}
}
