package com.drcome.project.challenge.mapper;

import java.util.List;

import com.drcome.project.challenge.ChallengeVO;

public interface ChallengeMapper {
	
	/* 현재 날짜 ToDoList */
	public List<ChallengeVO> getAllTodoList(String userId);
	
	/* 완료된 ToDoList */
	public List<ChallengeVO> getCompletedTodoList(String userId);
	
	/* ToDo 등록 */
	public int addTodoList(String challengeContent, String userId);
	
	/* ToDo 수정 */
	public int updateTodoList(int challengeNo, String userId);
	
	/* ToDo 수정 취소*/
	public int updatecancleTodoList(int challengeNo, String userId);
	
	/* ToDo 삭제 */
	public int deleteTodo(int challengeNo, String userId);
}
