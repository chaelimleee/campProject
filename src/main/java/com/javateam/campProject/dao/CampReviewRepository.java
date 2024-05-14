package com.javateam.campProject.dao;

import org.springframework.data.repository.CrudRepository;

import com.javateam.campProject.domain.CampReviewVO;

public interface CampReviewRepository extends CrudRepository<CampReviewVO, String> {

}