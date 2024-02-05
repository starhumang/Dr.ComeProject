package com.drcome.project.challenge.web;

import org.springframework.stereotype.Controller;

@Controller
public class ChallengeController {

/*	@Autowired
	ChallengeService cservice;

	@GetMapping("/challenge")
	public String home() {
		return "challenge/calendar";
	}
	
	@GetMapping("/todolist")
	@ResponseBody
	public Map<String, Object> getTodoList(int searchType) {
		
		System.out.println("searchType " + searchType);
		
		List<ChallengeVO> todoList = cservice.getTodoList(searchType, userId);
		
		Map<String, Object> todolist = new Map();
		todolist.put("todoList", todoList);
		
		return data;
	}
	
	@PostMapping("/todolist/insert")
	@ResponseBody
	public Map<String, Object> addTodoList(String challengeContent, int searchType) {
		
		int isSuccess = cservice.addTodoList(challengeContent, userId);
		System.out.println(isSuccess > 0 ? "성공" : "실패");
		
		Map<String, Object> data = new Map<>();
		data.put("isSuccess", isSuccess);
		data.put("todoList", cservice.getTodoList(searchType, userId));
	
		return data;
	}

	@GetMapping("/todolist/update")
	@ResponseBody
	public Map<String, Object> updateComYnOfTodoList(int searchType, int idx) {
		
		int result = cservice.updateTodoList(idx);
		System.out.println(result);
		
		Map<String, Object> data = new Map<>();
		data.put("todoList", cservice.getTodoList(searchType, userId));
		 
		return data;
	}
	
	@PostMapping("/todolist/delete")
	@ResponseBody
	public Map<String, Object> deleteTodo(int idx, int searchType) {
		
		cservice.deleteTodo(idx, userId);

		List<ChallengeVO> todoList = cservice.getTodoList(searchType, userId);
		
		Map<String, Object> data = new Map<String, Object>();
		data.put("todoList", todoList);
		
		return data;
	}*/
}
