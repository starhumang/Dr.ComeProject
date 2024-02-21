package com.drcome.project.challenge.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drcome.project.challenge.ChallengeVO;
import com.drcome.project.challenge.mapper.ChallengeMapper;
import com.drcome.project.challenge.service.ChallengeService;

@Service
public class ChallengeServiceImpl implements ChallengeService {

	@Autowired
	ChallengeMapper cmapper;

	@Override
	public List<ChallengeVO> getTodoList(String userId, String date) {
		return cmapper.getAllTodoList(userId, date);
	}

	@Override
	public List<ChallengeVO> clearToDo(String userId, String date) {
		return cmapper.getCompletedTodoList(userId, date);
	}

	@Override
	public int addTodoList(ChallengeVO cvo) {
		return cmapper.addTodoList(cvo);
	}

	@Override
	public int updateTodoList(String userId, int challengeNo) {
		return cmapper.updateTodoList(challengeNo, userId);
	}

	@Override
	public int cancleupdateTodo(String userId, int challengeNo) {
		return cmapper.updatecancleTodoList(challengeNo, userId);
	}

	@Override
	public boolean deleteTodo(int challengeNo, String userId) {
		int result = cmapper.deleteTodo(challengeNo, userId);
		return result == 1 ? true : false;
	}

	@Override
	public List<Map<String, Object>> SuccessToDo(String userId) {
		return cmapper.countAll(userId);
	}

	@Override
	public List<Map<String, Object>> ReserveList(String userId) {
		return cmapper.reserveList(userId);
	}

	
}
