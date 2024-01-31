package com.drcome.project.medical;

import java.util.Date;

import lombok.Data;

@Data
public class DoctorTimeVO {
	private String time;
	private String day;
	private String minTime;
	private String maxTime;
	private Date registerDate;
}
