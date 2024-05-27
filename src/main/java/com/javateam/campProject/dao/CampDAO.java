package com.javateam.campProject.dao;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javateam.campProject.domain.CampEntity;
import com.javateam.campProject.domain.CampImgsVO;
import com.javateam.campProject.domain.CampReviewVO;
import com.javateam.campProject.domain.ReservationVO;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class CampDAO {
	
	@Autowired
	SqlSession sqlSession;

	public CampEntity selectCamp(int id) {
		return sqlSession.selectOne("mapper.Camp.selectCamp",id);
	}

	public List<CampImgsVO> selectCampById(int id) {
		return sqlSession.selectList("mapper.Camp.selectCampById",id);
	}

//	public List<CampReviewVO> selectCampReviewsPage(String campName, int page) {
//		Map<String, Object> params = new HashMap<>();
//	    params.put("campName", campName);
//	    params.put("page", page);
//	    return sqlSession.selectList("mapper.Camp.selectCampReviewsPage", params);
//	}

	public List<CampReviewVO> selectCampReviews(String campName) {
		return sqlSession.selectList("mapper.Camp.selectCampReviews", campName);
	}
	
	// 리뷰 카운트
	public int countCampReviews(String campName) {
		
		int result = 0;
		
		try {
			result = sqlSession.selectOne("mapper.Camp.countCampReviews",campName);
		} catch(NullPointerException e) {
			result = 0;
		}
		
		return result;
	}

	public List<ReservationVO> selectReservation(String email) {
		return sqlSession.selectList("mapper.Camp.selectReservation", email);
	}

	public ReservationVO selectReservationByRegDate(String email, Date regDate, int campId) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("email", email);
		params.put("regDate", regDate);
		params.put("campId", campId);

		return sqlSession.selectOne("mapper.Camp.selectReservationByRegDate", params);
	}

	public void insertReservation(int campId, String campName, String email, String name, Date date) {
		
		Map<String, Object> params = new HashMap<>();
	    params.put("campId", campId);
	    params.put("campName", campName);
	    params.put("email", email);
	    params.put("name", name);
	    params.put("date", date);

	    sqlSession.insert("mapper.Camp.insertReservation", params);
	}

	public void updateReservation(int id, Date date, int yesno) {
		
		Map<String, Object> params = new HashMap<>();
		params.put("id", id);
		params.put("date", date);
		params.put("yesno", yesno);
		
		sqlSession.update("mapper.Camp.updateReservation", params);
	}

	public void deleteReservation(int id) {
		sqlSession.delete("mapper.Camp.deleteReservation", id);
	}
	
}
