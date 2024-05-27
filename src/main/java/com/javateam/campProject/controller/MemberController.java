package com.javateam.campProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.campProject.domain.CampEntity;
import com.javateam.campProject.domain.ReservationVO;
import com.javateam.campProject.domain.UserRequestVO;
import com.javateam.campProject.service.CampingService;

import lombok.extern.java.Log;

@Controller
@Log
@RequestMapping("/member")
public class MemberController {
	
	@Autowired
	CampingService campService;
	
	@GetMapping("/reservationView.do")
	public String reservationView(@RequestParam("email") String email, Model model) {
		
		List<ReservationVO> reservationList = campService.selectReservation(email);
		log.info("reservationList.size() 크기확인 : "+reservationList.size());
		model.addAttribute("reservationList",reservationList);
		
		return "/member/reservation_view";
	}

}