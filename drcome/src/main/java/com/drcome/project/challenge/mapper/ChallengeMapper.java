package com.drcome.project.challenge.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.drcome.project.challenge.ChallengeVO;

public interface ChallengeMapper {
	
	/* 현재 날짜 ToDoList */
	public List<ChallengeVO> getAllTodoList(String userId, @Param("date") String date);
	
	/* 완료된 ToDoList */
	public List<ChallengeVO> getCompletedTodoList(String userId, @Param("date") String date);
	
	/* ToDo 등록 */
	public int addTodoList(ChallengeVO cvo);
	
	/* ToDo 수정 */
	public int updateTodoList(int challengeNo, String userId);
	
	/* ToDo 수정 취소*/
	public int updatecancleTodoList(int challengeNo, String userId);
	
	/* ToDo 삭제 */
	public int deleteTodo(int challengeNo, String userId);
	
	
	/* calendar 표시 */
	// 1 아이디, 총 개수
	public List<Map<String, Object>> countAll (String userId);
	
	public List<Map<String, Object>> reserveList (String userId);
	
}
