package com.drcome.project.challenge.service;

import java.util.List;

import com.drcome.project.challenge.ChallengeVO;

public interface ChallengeService {

	/* TodoList */
	public List<ChallengeVO> getTodoList(String userId);
	
	/* 완료된 TodoList */
	public List<ChallengeVO> clearToDo(String userId);

	/* TodoList 추가 */ 
	public int addTodoList(String ChallengeContent, String userId);
	
	/* Todo 수정 */ 
	public int updateTodoList(String userId, int challengeNo);
	
	/* Todo 수정 취소 */ 
	public int cancleupdateTodo(String userId, int challengeNo);
	
	/* Todo 삭제 */ 
	public boolean deleteTodo(int challengeNo, String userId);
}
