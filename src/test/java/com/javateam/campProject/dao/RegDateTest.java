package com.javateam.campProject.dao;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.javateam.campProject.domain.ReservationVO;
import com.javateam.campProject.domain.User;
import com.javateam.campProject.service.CustomOAuth2UserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class RegDateTest {
	
	@Autowired
	CampDAO campDAO;
	
	@Test
	public void test() {
		ReservationVO reservationVO = campDAO.selectReservationByRegDate("cofla9999@naver.com",
				Date.valueOf("2024-05-26"), 7408);
		log.info("reservationVO 확인:" + reservationVO);
	}
}
