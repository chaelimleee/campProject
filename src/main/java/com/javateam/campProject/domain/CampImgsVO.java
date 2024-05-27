package com.javateam.campProject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="camp_imgs_tbl3")
@SequenceGenerator(
	    name = "CAMP_IMGS_TBL_SEQ_GENERATOR",
	    sequenceName = "CAMP_IMGS_TBL_SEQ",
	    initialValue = 1,
	    allocationSize = 1)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampImgsVO {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "CAMP_IMGS_TBL_SEQ_GENERATOR")
	private int id;

	@Column(name="img_id")
	private int imgId;
	
	@Column(name="camp_name")
	private String campName;
	
	@Column(name="img_name1")
	private String imgName1;
	
	@Column(name="img_name2")
	private String imgName2;
	
	@Column(name="img_name3")
	private String imgName3;

}
