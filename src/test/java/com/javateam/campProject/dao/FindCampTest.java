package com.javateam.campProject.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.javateam.campProject.domain.CampEntity;
import com.javateam.campProject.domain.User;
import com.javateam.campProject.service.CampingService;
import com.javateam.campProject.service.CustomOAuth2UserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class FindCampTest {
	
	@Autowired
	CampingService campService;

	@Test
	public void test() {
		CampEntity camp = campService.findCamp(5676);
		log.info("camp확인 : " + camp);
	}
}
