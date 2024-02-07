package com.drcome.project.challenge.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.drcome.project.challenge.ChallengeVO;
import com.drcome.project.challenge.service.ChallengeService;

@Controller
public class ChallengeController {

	@Autowired
	ChallengeService cservice;

	@GetMapping("/challenge")
	public String home(Principal principal, String userId, Model model) {
		
		userId = principal.getName();
		
		 //model.addAttribute("successToDo", cservice.SuccessToDo(userId));
		 //model.addAttribute("successToDo", cservice.ReserveList(userId));
		
		List<Object> cList = new ArrayList<>();
		
		cList.addAll(cservice.SuccessToDo(userId));
		cList.addAll(cservice.ReserveList(userId));
		
		model.addAttribute("successToDo", cList);
		System.out.println("멍멍" + cList);

		return "challenge/calendar";
	}

	@GetMapping("/todolist/{date}")
	public String getTodoList(@PathVariable String date, Principal principal, String userId, Model model) {
		userId = principal.getName();

		List<ChallengeVO> todoList = cservice.getTodoList(userId, date);
		List<ChallengeVO> ctodoList = cservice.clearToDo(userId, date);
		
		model.addAttribute("todolist", todoList);
		System.out.println(todoList);
		model.addAttribute("completetodo", ctodoList);
		System.out.println("com" + ctodoList);
		model.addAttribute("date", date);

		return "challenge/todolist";
	}
	
	/* 등록 페이지 */
	/*
	 * @GetMapping("/todoinsert") public String insertToDoList() { return
	 * "challenge/todolist"; }
	 */

	/* 등록 process */
	@PostMapping("/todoinsert/{date}")
	@ResponseBody
	public Map<String, Object> addTodoList(@PathVariable String date, Principal principal, String challengeContent, String userId) {
		userId = principal.getName();

		Map<String, Object> addTodoList = new HashMap();
		System.out.println("challenge" + challengeContent);
		addTodoList.put("result", challengeContent);

		cservice.addTodoList(date, challengeContent, userId);

		return addTodoList;
	}

	@PostMapping("/todoupdate/{challengeNo}")
	@ResponseBody
	public String updateTodoList(Principal principal, String userId, @PathVariable Integer challengeNo) {
		userId = principal.getName();
		cservice.updateTodoList(userId, challengeNo);
		return "challenge/todolist";
	}
	
	@PostMapping("/todoupdatecancle/{challengeNo}")
	@ResponseBody
	public String updateCancleTodo(Principal principal, String userId, @PathVariable Integer challengeNo) {
		userId = principal.getName();
		cservice.cancleupdateTodo(userId, challengeNo);
		return "challenge/todolist";
	}

	@GetMapping("/tododelete/{challengeNo}")
	public String deleteTodo(Principal principal, String userId, @PathVariable Integer challengeNo) {
		userId = principal.getName();
		cservice.deleteTodo(challengeNo, userId);
		return "redirect:todolist";
	}


}
