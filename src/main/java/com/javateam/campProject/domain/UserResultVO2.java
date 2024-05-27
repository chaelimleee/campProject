package com.javateam.campProject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResultVO2 {

	/** 결과셋 아이디 */
	@Id
	@Column
	private int id;

	/** 사용자 아이디 */
	@Column(name="user_id")
	private String userId;

	/** 행선예정지 ex) 강릉 */
	@Column(name="destination")
	private String destination;

	/** 캠핑장 아이디  */
	@Column(name="camp_id")
	private int campId;

	/** 캠핑장명  */
	@Column(name="camp_name")
	private String campName;

	/** 캠핑장 전화번호  */
	@Column(name="phone")
	private String phone;

	/** 캠핑장 간단 소개 0522 leee 추가 */
	@Column(name="facil_characteristics")
	private String facilCharacteristics;

	/** 행선 시기 ex) 2024년 5월 10일 운영 가능 */
	private String enableOps;

	/** 동행자 수 */
	@Column(name="number_of_people")
	private int numberOfPeople;

	/** 전기 사용가 여부 */
	@Column(name="is_electricity")
	private int isElectricity;

	/** WIFI 인터넷 사용가 여부 */
	@Column(name="is_wifi")
	private int isWifi;

	/** 캠프파이어 가능 여부 */
	@Column(name="is_campfire")
	private int isCampfire;

	/** 온수 가능 여부 */
	@Column(name="is_heater")
	private int isHeater;

	/** 풀장 가능 여부 */
	@Column(name="is_pool")
	private int isPool;

	/** 놀이터 가능 여부 */
	@Column(name="is_playground")
	private int isPlayground;

	/** 취사 시설 구비 여부 */
	@Column(name="is_sink")
	private int isSink;

	/** 산책로 사용 가능 여부 */
	@Column(name="is_trail")
	private int isTrail;

	/** 수상 레저 가능 여부 */
	@Column(name="is_maritime_leisure")
	private int isMaritimeLeisure;

	/** 낚시 가능 여부 */
	@Column(name="is_fishing")
	private int isFishing;

	/** 전체 요구사항 반영율 */
	@Column(name="satisfaction")
	private int satisfaction;

	/** 캠프장 도로명 주소 */
	@Column(name="road_address")
	private String roadAddress;

	/** 캠프장 지번 주소 */
	@Column(name="jibun_address")
	private String jibunAddress;

	/** 캠프장 위도 */
	@Column(name="latitude")
	private String latitude;

	/** 캠프장 경도 */
	@Column(name="longitude")
	private String longitude;

	/** 네이버 별점 */
	@Column(name="avg_rating")
	private String avgRating;
	
	/** 네이버 리뷰 */
	@Column(name="reviews")
	private String reviews;

	/** 네이버 리뷰(긍정지수) */
	@Column(name="review_positive")
	private String reviewPositive;

	/** 네이버 리뷰(부정지수) */
	@Column(name="review_negative")
	private String reviewNegative;

	/** 캠핑장 이미지 */
	@Column(name="camp_img")
	private String campImg;

	/** 캠핑장 상세이미지1 0523 leee 추가 */
	@Column(name="img_name1")
	private String imgName1;
	
	/** 캠핑장 상세이미지2 */
	@Column(name="img_name2")
	private String imgName2;

	/** 캠핑장 상세이미지3 */
	@Column(name="img_name3")
	private String imgName3;

	/** 캠핑장 상세설명 */
	@Column(name="facil_detail")
	private String facilDetail;

	/** 캠핑장 평일 운영*/
	@Column(name="weekday_op_status")
	private String weekdayOpStatus;
	
	/** 캠핑장 주말 운영 */
	@Column(name="weekend_op_status")
	private String weekendOpStatus;

	/** 캠핑장 홈페이지 */
	@Column(name="homepage")
	private String homepage;

	/** 캠핑장 유형 */
	@Column(name="cate3")
	private String cate3;

	/** 캠핑장 시도 */
	@Column(name="sido_name")
	private String sidoName;
	
	/** 캠핑장 시군구 */
	@Column(name="sigugun_name")
	private String sigugunName;

	/** 캠핑장 시군구 */
	@Column(name="sub_title")
	private String subTitle;
	

}