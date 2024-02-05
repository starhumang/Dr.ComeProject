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
	public List<ChallengeVO> getTodoList(int searchType, String userId) {
		if(searchType == 0) {
			return cmapper.getAllTodoList(userId);
		} else if(searchType == 1) {
			return cmapper.getActiveTodoList(userId);
		} else {
			return cmapper.getCompletedTodoList(userId);
		}
	}

	@Override
	public int addTodoList(String challengeContent, String userId) {
		return cmapper.addTodoList(challengeContent, userId);
	}

	@Override
	public int updateTodoList(int idx) {
		return cmapper.updateTodoList(idx);
	}

	@Override
	public int deleteTodo(int idx, String userId) {
		return cmapper.deleteTodo(idx, userId);
	}

}
