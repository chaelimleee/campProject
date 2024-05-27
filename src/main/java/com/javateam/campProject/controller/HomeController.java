package com.javateam.campProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.javateam.campProject.domain.UserRequestVO;

import lombok.extern.java.Log;

@Controller
@Log
public class HomeController {
	
	@GetMapping("/")
	public String home() {
		log.info("home");
		return "index2";
	} //
	
//	@GetMapping("/login/naver")
//	public String welcome() {
//		log.info("welcome");
//		return "welcome";
//	} //
	
	@GetMapping("/login/oauth2/authorization/naver")
	public String welcome() {
		log.info("welcome");
		return "index2";
	} //
	
	


}