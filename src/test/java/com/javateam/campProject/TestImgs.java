package com.javateam.campProject;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.javateam.campProject.domain.UserResultVO2;
import com.javateam.campProject.service.CampingService;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@Slf4j
public class TestImgs {
	
	@Autowired
	CampingService campsurvice;
	
	//0523 leee 테스트함. 이미지 3개 잘 나오는지. sql문제였음. 
	@Test
	void testImg() {
		List<String> imgs = new ArrayList<>();
		imgs = campsurvice.campImgs(7236);
		log.info("imgs 확인 : " + imgs.toString() );
		
	}
	
//	@Test
//	void test() {
//		UserResultVO2 userResult = null;
//		List<UserResultVO2> recommList = new ArrayList<>();
//		List<String> campImgsName = campsurvice.campImgs(4963);
//		log.info("campImgsName확인:" +campImgsName);
//		if(campImgsName .isEmpty()) {
//			try {
//				userResult.setImgName1(campImgsName.get(0));
//				userResult.setImgName2(campImgsName.get(1));
//				userResult.setImgName3(campImgsName.get(2));
//				log.info("여러개 이미지 확인:" + userResult.getImgName1());
//				log.info("여러개 이미지 확인:" + userResult.getImgName2());
//				log.info("여러개 이미지 확인:" + userResult.getImgName3());
//			}catch (Exception e) {
//				log.info("해당 캠핑장의 상세 이미지가 없습니다. ");
//			}
//		} 
//	}
	
	

}
