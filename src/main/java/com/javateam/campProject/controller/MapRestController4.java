package com.javateam.campProject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javateam.campProject.domain.UserRequestVO;
import com.javateam.campProject.domain.UserResultVO2;
import com.javateam.campProject.service.CampingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MapRestController4 {

	@Autowired
	CampingService campSvc;

	@PostMapping("/mapRest4")
	public ResponseEntity<Object> mapRestService(@ModelAttribute UserRequestVO userRequestVO) {

		log.info("mapRest4");

		log.info("UserRequestVO : {}" , userRequestVO);

		// 질의(Query) 판정
		List<UserResultVO2> campList = null;

		// 응답(response) 정보
		ResponseEntity<Object> response = null;

		try {

			campList = campSvc.predictCampWithNaver(userRequestVO)
					   .stream()
					   .sorted((o1, o2) -> o2.getSatisfaction() - o1.getSatisfaction()) // 만족도 순으로 내림차순 정렬(rank-Top)
					   .limit(10)
					   .toList();

			if (campList.isEmpty() == true) {

				log.info("해당 정보가 없습니다.");
				response = new ResponseEntity<>("해당 정보가 존재하지 않습니다.", HttpStatus.NO_CONTENT);

			} else { // 검색 결과 있음

				response = new ResponseEntity<>(campList, HttpStatus.OK);

			} // 리스트 확보 여부

		} catch (Exception e) {
			log.error("DB 에러" + e);
			response = new ResponseEntity<>("서버 응답에 문제가 있습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		log.info("response : " + response.getStatusCode());

		return response;
	} //

}