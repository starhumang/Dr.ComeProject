package com.drcome.project.common.mapper;

import com.drcome.project.common.service.AlarmDao;

public interface AlarmMapper {

	// 알람정보 인서트
	public int insertAlarm(AlarmDao dao);
	
	//public 알람정보 가져오기 
	public int selectAlarm(String uid);
	
	//약국 알람정보 인서트 
	public int insertAlarmPharmacy(AlarmDao dao);
	
	//public 약국알람정보 가져오기 
	public int selectAlarmPharmacy(String uid);
}
