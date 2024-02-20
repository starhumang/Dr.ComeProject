package com.drcome.project.common.service;

import lombok.Data;

@Data
public class AlarmDao {

	private String userId;
	private String contentCode;
	private String checkCode;
	private String prefix;
	private int reserveNo;
	private int pharmacySelectno;

}
