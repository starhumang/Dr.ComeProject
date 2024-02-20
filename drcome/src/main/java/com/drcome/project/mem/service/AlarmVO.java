package com.drcome.project.mem.service;

import lombok.Data;

@Data
public class AlarmVO {
	private Integer alarmNo;
	private String contentCode;
	private String prefix;
	private Integer reserveNo;
	private Integer pharmacySelectno;
	private String checkCode;
}
