package com.drcome.project.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drcome.project.common.mapper.AlarmMapper;
import com.drcome.project.common.service.AlarmDao;
import com.drcome.project.common.service.AlarmService;

@Service
public class AlarmServiceImpl implements AlarmService {

	@Autowired
	AlarmMapper mapper;

	// 알람 인서트
	@Override
	public int saveAlarm(AlarmDao dao) {
		return mapper.insertAlarm(dao);
	}

}
