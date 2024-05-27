package com.javateam.campProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OutsideController {
	
	// 0524 leee 외부 파일사용해서 상세이미지 3개 넣기 위해 생성
	
	@RequestMapping(value="/out/outsideFolder", method=RequestMethod.GET)
	public String outsideFolder() throws Exception {
		return "/out/outsideFolder";
	}

}
