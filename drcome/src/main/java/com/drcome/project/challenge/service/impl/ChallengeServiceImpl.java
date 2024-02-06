package com.drcome.project.challenge.service.impl;

import java.util.List;

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
	public List<ChallengeVO> getTodoList(String userId) {
		return cmapper.getAllTodoList(userId);
	}

	@Override
	public List<ChallengeVO> clearToDo(String userId) {
		return cmapper.getCompletedTodoList(userId);
	}

	@Override
	public int addTodoList(String ChallengeContent, String userId) {
		return cmapper.addTodoList(ChallengeContent, userId);
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
}
