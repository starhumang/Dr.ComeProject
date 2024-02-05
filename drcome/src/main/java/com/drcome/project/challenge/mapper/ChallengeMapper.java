package com.drcome.project.challenge.mapper;

import java.util.List;

import com.drcome.project.challenge.ChallengeVO;

public interface ChallengeMapper {
	public List<ChallengeVO> getAllTodoList(String userId);
	public List<ChallengeVO> getActiveTodoList(String userId);
	public List<ChallengeVO> getCompletedTodoList(String userId);
	public int addTodoList(String challengeContent, String userId);
	public int updateTodoList(int idx);
	public int deleteTodo(int idx, String userId);
}
