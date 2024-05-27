package com.javateam.campProject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javateam.campProject.dao.CampRepository;
import com.javateam.campProject.domain.CampEntity;
import com.javateam.campProject.domain.CampImgsVO;
import com.javateam.campProject.domain.CampReviewVO;
import com.javateam.campProject.domain.PageVO;
import com.javateam.campProject.domain.UserRequestVO;
import com.javateam.campProject.service.CampingService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MapDemoController {
	
	@Autowired
	CampingService campService;

	@GetMapping("/map_rest")
	public String mapRest(Model model) {

		model.addAttribute("userRequestVO", new UserRequestVO());

		return "map_rest";
	}

	@GetMapping("/map_rest_naver")
	public String mapRestWithNaver(Model model) {

		model.addAttribute("userRequestVO", new UserRequestVO());

		return "map_rest_naver";
	}

	@GetMapping("/map_rest_naver2")
	public String mapRestWithNaverDemo(Model model) {
		
		model.addAttribute("userRequestVO", new UserRequestVO());
		
		return "map_rest_naver2";
	}

	@GetMapping("/map_rest_naver3")
	public String mapRestWithNaverDemo3(Model model) {
		
		model.addAttribute("userRequestVO", new UserRequestVO());
		
		return "map_rest_naver3";
	}
	@GetMapping("/map_rest_naver4")
	public String mapRestWithNaverDemo4(Model model) {
		
		model.addAttribute("userRequestVO", new UserRequestVO());
		
		return "map_rest_naver4";
	}
	
//	@GetMapping("/map_rest_naver4")
//	public String mapRestWithNaverDemo4(@RequestParam("camp_id") int campId, 
//						  @RequestParam("camp_title") String campTitle, Model model) {
//		
//		log.info("캠프아이디, 이름 확인 : " + campId +","+ campTitle);
//		
//		List<CampEntity> campList = campService.findCamp(campId);
//		List<CampImgsVO> campByIdList = campService.selectCampById(campId);
//		log.info("캠프 확인 : " + campByIdList );
//		
//		model.addAttribute("userRequestVO", new UserRequestVO());
//		model.addAttribute("campId", campId);
//		model.addAttribute("campTitle", campTitle);
//		model.addAttribute("campList", campList);
//		model.addAttribute("campByIdList", campByIdList);
//		
//		return "map_rest_naver4";
//	}

	@GetMapping("/map_view")
	public String mapview(@RequestParam("camp_id") int campId, 
						  @RequestParam("camp_title") String campTitle, Model model) {
		
		log.info("캠프아이디, 이름 확인 : " + campId +","+ campTitle);
		
		CampEntity campList = campService.findCamp(campId);
		List<CampImgsVO> campByIdList = campService.selectCampById(campId);
//		List<CampReviewVO> campReviewList = campService.selectCampReviews(campTitle, currPage);
		
		int count = campService.countCampReviews(campTitle);
		
		// 리뷰중에서 긍정리뷰만 5개 정도만 보여줌. 0526 leee
		List<CampReviewVO> campReviewList = campService.selectCampReviews(campTitle, (count >= 5 ? 5 : count));
		
		log.info("캠프 확인 : " + campByIdList );
		log.info("캠프 리뷰 확인 : " + campReviewList );
		
		model.addAttribute("userRequestVO", new UserRequestVO());
		model.addAttribute("campId", campId);
		model.addAttribute("campTitle", campTitle);
		model.addAttribute("campList", campList);
		model.addAttribute("campByIdList", campByIdList);
		model.addAttribute("campReviewList", campReviewList);
		
		return "map_view";
	}
//	@GetMapping("/map_view")
//	public String mapview(@RequestParam("camp_id") int campId, 
//			@RequestParam("camp_title") String campTitle, 
//			@RequestParam(value = "currPage", defaultValue = "1") int currPage,
//			@RequestParam(value = "limit", defaultValue = "5") int limit, Model model) {
//		
//		log.info("캠프아이디, 이름 확인 : " + campId +","+ campTitle);
//		
//		List<CampEntity> campList = campService.findCamp(campId);
//		List<CampImgsVO> campByIdList = campService.selectCampById(campId);
//		List<CampReviewVO> campReviewList = campService.selectCampReviews(campTitle, currPage);
//		int campReviewCount = campService.countCampReviews(campTitle);
//		log.info("캠프 확인 : " + campByIdList );
//		log.info("캠프 리뷰 확인 : " + campReviewList );
//		
//		int maxPage=(int)((double)campReviewCount/limit+0.95); //0.95를 더해서 올림 처리
//		// int startPage = (((int) ((double)currPage / 10 + 0.9)) - 1) * 10 + 1;
//		int startPage = 1;
//		// 현재 페이지에 보여줄 마지막 페이지 수(10, 20, 30, ...)
//		int endPage = PageVO.getEndPage(currPage, limit, campReviewCount);
//		
//		PageVO pageVO = new PageVO();
//		pageVO.setCurrPage(currPage);
//		pageVO.setListCount(campReviewCount);
//		pageVO.setMaxPage(PageVO.getMaxPage(campReviewCount, limit));
//		pageVO.setStartPage(PageVO.getStartPage(currPage, limit));
//		//pageVO.setEndPage(PageVO.getEndPage(currPage, limit));
//		
//		pageVO.setPrePage(pageVO.getCurrPage() - 1 < 1 ? 1 : pageVO.getCurrPage() - 1);
//		pageVO.setNextPage(pageVO.getCurrPage() + 1 > pageVO.getEndPage() ? pageVO.getEndPage() : pageVO.getCurrPage() + 1);
//		
//		model.addAttribute("pageVO", pageVO);
//		model.addAttribute("startPage", startPage);
//		model.addAttribute("endPage", endPage);
//		model.addAttribute("listCount", campReviewCount);// 추가 및 수정
//		
//		model.addAttribute("userRequestVO", new UserRequestVO());
//		model.addAttribute("campId", campId);
//		model.addAttribute("campTitle", campTitle);
//		model.addAttribute("campList", campList);
//		model.addAttribute("campByIdList", campByIdList);
//		model.addAttribute("campReviewList", campReviewList);
//		
//		return "map_view";
//	}

}