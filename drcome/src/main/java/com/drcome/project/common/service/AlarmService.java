package com.drcome.project.common.service;

public interface AlarmService {

	// 알람 인서트
	public int saveAlarm(AlarmDao dao);
  
	//알람 여부 조회 
	public int checkAlarm (String uid);
	
	// 약국 반환 알림 인서트
	public int saveAlarmPharmacy(AlarmDao dao);
	
	// 약국 반환 알람 여부 조회
	public int checkAlarmPharmacy(String uid);
  
}
