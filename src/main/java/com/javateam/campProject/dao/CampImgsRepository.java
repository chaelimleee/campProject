package com.javateam.campProject.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.javateam.campProject.domain.CampEntity;
import com.javateam.campProject.domain.CampImgsVO;

public interface CampImgsRepository extends CrudRepository<CampImgsVO, Integer> {

//	@Query(value = "select img_name1,img_name2,img_name3 "
//				 + "from camp_imgs_tbl i , camp_info_tbl c "
//				 + "where i.id = c.id and i.id = :id ;", nativeQuery=true)
//	List<String> findByCampImgs(@Param("id") int id);

	@Query(value = "SELECT distinct img_name1, img_name2, img_name3 FROM camp_imgs_tbl3 WHERE id = :id", nativeQuery=true)
    String findByCampImgs(@Param("id") int id);
} //