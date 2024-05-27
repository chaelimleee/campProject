package com.javateam.campProject.predict;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.javateam.campProject.domain.UserRequestVO;
import com.javateam.campProject.service.CampingService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class PredictTest {
	
	@Autowired
	CampingService campSvc;

	@Test
	public void test() {

		UserRequestVO userRequestVO;

		// 사용자 요구사항
		userRequestVO = UserRequestVO.builder()
									 .id(1)
									 .userId("abcd1234")
									 .time(LocalDate.of(2024, 5, 10))
									 .destination("태안")
									 .isElectricity(1)
									 .isWifi(1)
									 .isCampfire(0)
									 .isHeater(1)
									 .isPool(0)
									 .isPlayground(0)
									 .isSink(0)
									 .isTrail(1)
									 .isMaritimeLeisure(0)
									 .isFishing(1)
									 .build();

		log.info("추천 캠핑장 리스트");
		campSvc.predictCamp(userRequestVO)
			   .stream()
			   .sorted((o1, o2) -> o2.getSatisfaction() - o1.getSatisfaction()) // 만족도 순으로 내림차순 정렬(rank-Top)
			   .limit(10) // 10개 정도만 출력
			   .forEach(x -> { log.info("{}", x); });
	} //
	

}
