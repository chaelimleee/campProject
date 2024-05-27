package com.javateam.campProject.controller;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.campProject.domain.CampEntity;
import com.javateam.campProject.domain.ReservationVO;
import com.javateam.campProject.domain.User;
import com.javateam.campProject.service.CampingService;
import com.javateam.campProject.service.CustomOAuth2UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ReservationController {
	
	@Autowired
	CampingService campService;
	
	@Autowired
	CustomOAuth2UserService customOAuth2UserService;
	
	//0526 leee 예약서비스
	@GetMapping("/reservation.do")
	public String reservation(@RequestParam("camp_id") int campId,	
							  @RequestParam("camp_name") String campName,
							  @RequestParam("email") String email, Model model) {
		
		log.info("campId, email 확인 : {}, {}",campId,email);
		User user = customOAuth2UserService.getUser(email).get();
		CampEntity campEntity = campService.findCamp(campId);
		
		log.info("user확인 : " + user);
		log.info("campEntity확인 : " + campEntity);
		
		String msg = "";
		String movePage = "";
		
		// 기존 예약 정보 조회 0526
		ReservationVO reservation = campService.selectReservationByRegDate(email, new Date(System.currentTimeMillis()), campId);
		
		if(reservation == null) {
			
			campService.insertReservation(campId, campEntity.getCampName(), email, user.getName(),new Date(System.currentTimeMillis()));
			msg = "예약이 성공적으로 완료되었습니다.";
			movePage = "/member/reservationView.do?email="+email;

			List<ReservationVO> reservationList = campService.selectReservation(email);
			model.addAttribute("reservationList",reservationList);
			
		} else {
			
			msg = "해당날짜에 대한 예약정보가 이미 존재합니다.";
			movePage = "/map_view?camp_title="+campName+"&camp_id="+campId ;
		}
		
		model.addAttribute("movePage",movePage);
		model.addAttribute("errMsg",msg);
		
		return "/error/error";
	}

}
