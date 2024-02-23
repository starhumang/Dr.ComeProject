package com.drcome.project.challenge.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.drcome.project.challenge.ChallengeVO;
import com.drcome.project.challenge.service.ChallengeService;

/** 일반 사용자 챌린지 페이지(예약 내역 확인, ToDoList 등록)
 * 
 * @author 최해주
 * @since 2024.02
 *
 */

@Controller
public class ChallengeController {

	@Autowired
	ChallengeService cservice;

	/**
	 * 챌린지 달력 - 예약내역, ToDo 성공여부 조회
	 * @param principal 세션
	 * @param userId 사용자 조회 조건
	 * @param model 화면 모델
	 * @return challenge/calendar
	 */
	@GetMapping("/challenge")
	public String home(Principal principal, String userId, Model model) {
		
		userId = principal.getName();
		
		 //model.addAttribute("successToDo", cservice.SuccessToDo(userId));
		 //model.addAttribute("successToDo", cservice.ReserveList(userId));
		
		List<Object> cList = new ArrayList<>();
		
		cList.addAll(cservice.SuccessToDo(userId));
		cList.addAll(cservice.ReserveList(userId));
		
		model.addAttribute("successToDo", cList);

		return "challenge/calendar";
	}

	/**
	 * 날짜별 TodoList 조회
	 * @param date 검색 조건
	 * @param principal 세션
	 * @param model 화면 모델
	 * @return challenge/todolist
	 */
	@GetMapping("/todolist/{date}")
	public String getTodoList(@PathVariable String date, Principal principal,  Model model) {
		String userId = principal.getName();

		List<ChallengeVO> todoList = cservice.getTodoList(userId, date);
		List<ChallengeVO> ctodoList = cservice.clearToDo(userId, date);
		
		model.addAttribute("todolist", todoList);
		model.addAttribute("completetodo", ctodoList);
		model.addAttribute("date", date);

		return "challenge/todolist";
	}

	/**
	 * 날짜별 ToDoList 등록
	 * @param date 검색 조건
	 * @param principal 세션
	 * @param challengeContent 등록 내용
	 * @return addTodoList
	 */
	/* 등록 process */
	@PostMapping("/todoinsert")
	@ResponseBody
	public ChallengeVO addTodoList(@RequestBody ChallengeVO cvo, Principal principal) {
		
		String userId = principal.getName();
		cvo.setUserId(userId);

		cservice.addTodoList(cvo);

		return cvo;
	}

	/**
	 * ToDo 성공여부 수정(fail -> success)
	 * @param principal 세션
	 * @param challengeNo 수정 조건
	 * @return challenge/todolist
	 */
	@PostMapping("/todoupdate/{challengeNo}")
	@ResponseBody
	public int updateTodoList(Principal principal, @PathVariable Integer challengeNo) {
		String userId = principal.getName();
		int result = cservice.updateTodoList(userId, challengeNo);
		return result;
	}
	
	
	/**
	 * ToDo 성공여부 수정(success -> fail)
	 * @param principal 세션
	 * @param challengeNo 수정 조건
	 * @return challenge/todolist
	 */
	@PostMapping("/todoupdatecancle/{challengeNo}")
	@ResponseBody
	public int updateCancleTodo(Principal principal, @PathVariable Integer challengeNo) {
		String userId = principal.getName();
		int result = cservice.cancleupdateTodo(userId, challengeNo);
		return result;
	}

	/**
	 * 등록된 ToDo 삭제
	 * @param principal 세션
	 * @param challengeNo 삭제 조건
	 * @return redirect:todolist
	 */
	@GetMapping("/tododelete/{challengeNo}")
	@ResponseBody
	public boolean deleteTodo(Principal principal, @PathVariable Integer challengeNo) {
		String userId = principal.getName();
		boolean delete = cservice.deleteTodo(challengeNo, userId);
		return delete;
	}


}
