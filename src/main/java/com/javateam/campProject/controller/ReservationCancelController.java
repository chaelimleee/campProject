package com.javateam.campProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.campProject.domain.ReservationVO;
import com.javateam.campProject.service.CampingService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ReservationCancelController {

	@Autowired
	CampingService campService;
	
	//0526 leee
	@GetMapping("/reservation_cancel.do")
	public String reservationCancel(@RequestParam("id") int id,
									@RequestParam("email") String email, Model model){
		
		List<ReservationVO> resList = null;
		
		try {
			campService.deleteReservation(id);
			resList = campService.selectReservation(email);
			
			if (resList.isEmpty() == true) {
				
				log.info("해당 정보가 없습니다.");
				
			} else {
				model.addAttribute("reservationList", resList);
			}
			
		} catch(Exception e) {
			log.error("DB 에러" + e);
		}
		
		return "/member/reservation_view";
	}
	
}
