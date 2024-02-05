package com.drcome.project.challenge.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.drcome.project.challenge.service.ChallengeService;

@Controller
public class ChallengeController {

	@Autowired
	ChallengeService cservice;

	@GetMapping("/challenge")
	public String home() {
		return "challenge/calendar";
	}
}
