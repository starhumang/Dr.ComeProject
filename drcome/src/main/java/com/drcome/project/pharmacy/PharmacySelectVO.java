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
}
