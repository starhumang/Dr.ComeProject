package com.drcome.project.common.mapper;

import com.drcome.project.common.service.AlarmDao;

public interface AlarmMapper {

	// 알람정보 인서트
	public int insertAlarm(AlarmDao dao);

	// 알람여부조회
	public int selectAlarm(String uid);

}
