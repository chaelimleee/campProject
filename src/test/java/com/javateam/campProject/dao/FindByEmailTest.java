package com.javateam.campProject.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.javateam.campProject.domain.User;
import com.javateam.campProject.service.CustomOAuth2UserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class FindByEmailTest {
	
	@Autowired
	CustomOAuth2UserService customOAuth2UserService;

	@Test
	public void test() {
		User user = customOAuth2UserService.getUser("cofla9999@naver.com").get();
		log.info("user확인 : "+user);
	}
}
