package com.drcome.project.challenge.service;

import java.util.List;
import java.util.Map;

import com.drcome.project.challenge.ChallengeVO;

public interface ChallengeService {

	/* TodoList */
	public List<ChallengeVO> getTodoList(String userId, String date);
	
	/* 완료된 TodoList */
	public List<ChallengeVO> clearToDo(String userId, String date);

	/* TodoList 추가 */ 
	public int addTodoList(ChallengeVO cvo);
	
	/* Todo 수정 */ 
	public int updateTodoList(String userId, int challengeNo);
	
	/* Todo 수정 취소 */ 
	public int cancleupdateTodo(String userId, int challengeNo);
	
	/* Todo 삭제 */ 
	public boolean deleteTodo(int challengeNo, String userId);
	
	public List<Map<String, Object>> SuccessToDo(String userId);
	
	public List<Map<String, Object>> ReserveList(String userId);
}
