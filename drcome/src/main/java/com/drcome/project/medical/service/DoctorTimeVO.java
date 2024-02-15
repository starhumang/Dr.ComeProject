package com.drcome.project.medical.service;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class DoctorTimeVO {
	private String time;
	private String day;
	private String minTime;
	private String maxTime;
	private Date registerDate;
	
    private List<String> timeArray;
}
