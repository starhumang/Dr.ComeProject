package com.drcome.project.challenge.service;

import java.util.List;

import com.drcome.project.challenge.ChallengeVO;

public interface ChallengeService {
	
	/* TodoList */
	public List<ChallengeVO> getTodoList(int searchType, String userId);
		
	/* TodoList 추가*/
	public int addTodoList(String challengeContent, String userId);
	
	/* Todo 수정*/
	public int updateTodoList(int idx);
	
	/* Todo 삭제*/
	public int deleteTodo(int idx, String userId);
}
