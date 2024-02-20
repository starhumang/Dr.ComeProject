package com.drcome.project.common.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drcome.project.common.mapper.AlarmMapper;
import com.drcome.project.common.service.AlarmDao;
import com.drcome.project.common.service.AlarmService;
import com.drcome.project.doctor.mapper.PatientMapper;

@Service
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	AlarmMapper mapper;

	@Autowired
	PatientMapper pmapper;

	// 알람테이블 인서트 + 입장하기로 상태 업데이트
	@Transactional
	@Override
	public int saveAlarm(AlarmDao dao) {
		// System.out.println("입장하기로업데이트");
		// 알람인서트
		int result = mapper.insertAlarm(dao);

		if (result > 0) {
			// 입장하기로 업데이트
			result = pmapper.updateEnter(dao);
		}
		return result;
	}

//	// 알람여부 조회
//	@Override
//	public int checkAlarm(String uid) {
//		return mapper.selectAlarm(uid);
//	}

	// 약국 반환 알람테이블 insert
	@Override
	public int saveAlarmPharmacy(AlarmDao dao) {
		return mapper.insertAlarmPharmacy(dao);
	}

	// 약국 반환 알람 조회
	@Override
	public int checkAlarmPharmacy(String uid) {
		return mapper.selectAlarmPharmacy(uid);
	}

}
