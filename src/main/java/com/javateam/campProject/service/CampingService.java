package com.javateam.campProject.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javateam.campProject.dao.CampDAO;
import com.javateam.campProject.dao.CampImgsRepository;
import com.javateam.campProject.dao.CampRepository;
import com.javateam.campProject.dao.CampReviewRepository;
import com.javateam.campProject.domain.CampEntity;
import com.javateam.campProject.domain.CampImgsVO;
import com.javateam.campProject.domain.CampReviewVO;
import com.javateam.campProject.domain.ReservationVO;
import com.javateam.campProject.domain.UserRequestVO;
import com.javateam.campProject.domain.UserResultVO;
import com.javateam.campProject.domain.UserResultVO2;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CampingService {

	@Autowired
	CampRepository campRepo;

	@Autowired
	CampImgsRepository campImgsRepo;

	@Autowired
	CampReviewRepository campReviewDAO;

	@Autowired
	CampDAO campDAO;

	private static final int EARTH_RADIUS = 6371; // 지구 반경(km)

	private static final double LATITUDE_DEGREE_PER_METER = 1.0 / (2 * Math.PI * EARTH_RADIUS * 1000 / 360);

	public static double getDistance(double lat1, double lon1, double lat2, double lon2) {

		double p1 = lat1 * Math.PI / 180;
		double p2 = lat2 * Math.PI / 180;
		double gp = (lat2 - lat1) * Math.PI / 180;
		double gl = (lon2 - lon1) * Math.PI / 180;

		double a = Math.sin(gp / 2) * Math.sin(gp / 2) +
				   Math.cos(p1) * Math.cos(p2) *
				   Math.sin(gl / 2) * Math.sin(gl / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		double d = EARTH_RADIUS * 1000 * c;

		return d; // meter
	}

    // 두 좌표 사이의 거리를 구하는 함수
    // getdistance(첫번째 좌표의 위도, 첫번째 좌표의 경도, 두번째 좌표의 위도, 두번째 좌표의 경도)
    public static double getDistance2(double lat1, double lon1, double lat2, double lon2) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) +
        			  Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1609.344;

        return dist; // 단위 meter
    }

	// This function converts decimal degrees to radians
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	// This function converts radians to decimal degrees
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
	    double latDiff = lat2 - lat1;
	    double lonDiff = lon2 - lon1;
	    return Math.sqrt(latDiff * latDiff + lonDiff * lonDiff);
	}

	// Haversine Formula(하버사인) 공식
	// 지국가 완전 구라는 전제하에 위도 1도의 거리 계산
	// 위도 1도의 거리 = 2 * Math.PI * (지구의 원주) * cos(위도) / 360
	// 참고) https://jinkpark.tistory.com/296

	// 특정 반경 위도 범위 측정
	public double[] getLatitudeRange(double latitude, int radiusInMeters) {

	    double degreeRange = radiusInMeters * LATITUDE_DEGREE_PER_METER;

	    double minLatitude = latitude - degreeRange;
	    double maxLatitude = latitude + degreeRange;

	    return new double[]{minLatitude, maxLatitude};
	}

	// 특정 반경 경도 범위 측정
	public double[] getLongitudeRange(double latitude, double longitude, int radiusInMeters) {

		double longitudeDegreePerMeter = 360 / (2 * Math.PI * EARTH_RADIUS * 1000 * Math.cos(Math.toRadians(latitude)));
	    double degreeRange =  longitudeDegreePerMeter * radiusInMeters;

	    double minLongitude = longitude - degreeRange;
	    double maxLongitude = longitude + degreeRange;

	    return new double[]{minLongitude, maxLongitude};
	}

	public List<CampEntity> loadCampData() {

		List<CampEntity> campings = new ArrayList<>();
		String filename = "한국문화정보원_전국 문화 여가 활동 시설(캠핑) 데이터_20221130.CSV";

		String[] campInfo;
		CampEntity campEntity;

		try {

			ClassPathResource resource = new ClassPathResource("/csv/" + filename);

			CSVReader csvReader
				= new CSVReader(new InputStreamReader(new FileInputStream(resource.getFile()), "EUC-KR"));

			csvReader.readNext(); // 컬럼명 제외

			while ((campInfo = csvReader.readNext()) != null) {

				// log.info("campName :{}", campInfo[0]);

				campEntity = CampEntity.builder()
										.campName(campInfo[0])
										.cate1(campInfo[1])
										.cate2(campInfo[2])
										.cate3(campInfo[3])
										.sidoName(campInfo[4])
										.sigugunName(campInfo[5])
										.eupmyundongName(campInfo[6])
										.ryName(campInfo[7])
										.bunjiName(campInfo[8])
										.roadName(campInfo[9])
										.buildingNum(campInfo[10])
										.latitude(campInfo[11])
										.longitude(campInfo[12])
										.zip(campInfo[13])
										.roadAddress(campInfo[14])
										.jibunAddress(campInfo[15])
										.phone(campInfo[16])
										.homepage(campInfo[17])
										.vendor(campInfo[18])
										.weekdayOpStatus(campInfo[19])
										.weekendOpStatus(campInfo[20])
										.springOpStatus(campInfo[21])
										.summerOpStatus(campInfo[22])
										.fallOpStatus(campInfo[23])
										.winterOpStatus(campInfo[24])
										.facilElectricity(campInfo[25])
										.facilHotWater(campInfo[26])
										.facilWifi(campInfo[27])
										.facilCampfire(campInfo[28])
										.facilTrail(campInfo[29])
										.facilPool(campInfo[30])
										.facilPlayground(campInfo[31])
										.facilMart(campInfo[32])
										.facilRestroom(campInfo[33])
										.facilShowerroom(campInfo[34])
										.facilSink(campInfo[35])
										.facilExtinguisher(campInfo[36])
										.surrFacilFishing(campInfo[37])
										.surrFacilTrail(campInfo[38])
										.surrFacilBeach(campInfo[39])
										.surrFacilMaritimeLeisure(campInfo[40])
										.surrFacilValley(campInfo[41])
										.surrFacilStream(campInfo[42])
										.surrFacilPool(campInfo[43])
										.surrFacilYouthExperience(campInfo[44])
										.surrFacilRuralExperience(campInfo[45])
										.surrFacilChildrensPlay(campInfo[46])
										.glamBed(campInfo[47])
										.glamTv(campInfo[48])
										.glamFreezer(campInfo[49])
										.glamInternet(campInfo[50])
										.glamRestroom(campInfo[51])
										.glamAircon(campInfo[52])
										.glamHeater(campInfo[53])
										.glamCookware(campInfo[54])
										.facilCharacteristics(campInfo[55])
										.facilDetail(campInfo[56])
										.regDate(Date.valueOf("2024-05-09").toString()) // 등록일은 임의 일자로 생성
										.campImg(campInfo[58])
										.build();

				campings.add(campEntity);

			} // while

		} catch (IOException e) {
			log.error("readCampingsFromCSV : {}", e);
			e.printStackTrace();
		} catch (CsvValidationException e) {
			log.error("readCampingsFromCSV : {}", e);
			e.printStackTrace();
		}

		log.info("camping size : {}", campings.size());

		return campings;
	}

	//0524 leee 상세페이지 개별 캠핑장 정보 가져오기. 
	@Transactional(readOnly = true)
	public CampEntity findCamp(int id) {
		return campDAO.selectCamp(id);
	}

	//0524 leee 상세페이지 개별 캠핑장 정보 가져오기. 
	@Transactional(readOnly = true)
	public List<CampImgsVO> selectCampById(int id) {
		return campDAO.selectCampById(id);
	}

//	//0524 leee 리뷰가져오기.
//	public List<CampReviewVO> selectCampReviewsPage(String campName, int page) {
//		return campDAO.selectCampReviewsPage(campName, page);
//	}

	//0524 leee 리뷰가져오기.5개만
	@Transactional(readOnly = true)
	public List<CampReviewVO> selectCampReviews(String campName, int n) {
		return campDAO.selectCampReviews(campName).subList(0, n);
	}
	
	//0524 leee 리뷰개수.
	public int countCampReviews(String campName) {
		return campDAO.countCampReviews(campName);
	}

	@Transactional(readOnly = true)
	public List<ReservationVO> selectReservation(String email) {
		return campDAO.selectReservation(email);
	}

	@Transactional(readOnly = true)
	public ReservationVO selectReservationByRegDate(String email, Date regDate, int campId) {
		return campDAO.selectReservationByRegDate(email, regDate, campId);
	}
	
	@Transactional
	public void insertReservation(int campId, String campName, String email, String name, Date date) {
		campDAO.insertReservation(campId, campName, email, name, date);
	}

	//0526 leee 수정
	@Transactional
	public void updateReservation(int id, Date date, int yesno) {
		campDAO.updateReservation(id, date, yesno);
	}

	/**
	 * 계절 판정
	 *
	 * @param campEntity
	 * @param date
	 * @return
	 */
	private boolean checkEnableSeason(CampEntity campEntity, LocalDate date) {

		boolean flag = false;

		int month = date.getMonthValue();

		flag = month >= 3 && month <= 5 ? campEntity.getSpringOpStatus().equals("봄 운영") :
			   month >= 6 && month <= 8 ? campEntity.getSummerOpStatus().equals("여름 운영") :
			   month >= 9 && month <= 11 ? campEntity.getSummerOpStatus().equals("가을 운영") :
			   campEntity.getSummerOpStatus().equals("겨울 운영");

		return flag;
	}

	/**
	 * 만족도 계산-2 : 네이버 평점 포함 VO
	 *
	 * @param userResult
	 * @return
	 */
	private int calcSatisfaction(UserResultVO2 userResult) {

		log.info("userResult : {}", userResult);

		int result = 0;
		int totalFactor = 11; // 총 만족도 조사 요건 ex) 지역, ..., 낚시 가능 여부
		int factorSum = 1; // 사용자 요청 지역과 일치한다는 전제로 +1로 시작

		factorSum = userResult.getEnableOps().contains("운영 가능") ? factorSum++ : factorSum;

		factorSum += userResult.getIsElectricity();
		factorSum += userResult.getIsWifi();
		factorSum += userResult.getIsCampfire();
		factorSum += userResult.getIsHeater();
		factorSum += userResult.getIsPool();
		factorSum += userResult.getIsPlayground();
		factorSum += userResult.getIsTrail();
		factorSum += userResult.getIsMaritimeLeisure();
		factorSum += userResult.getIsFishing();

		log.info("factorSum : {}", factorSum);
		log.info("totalFactor : {}", totalFactor);

		result = (int)((float)factorSum / (float)totalFactor * 100);

		log.info("만족도 : " + result);

		return result;
	}

	/**
	 * 만족도 계산
	 *
	 * @param userResult
	 * @return
	 */
	private int calcSatisfaction(UserResultVO userResult) {

		log.info("userResult : {}", userResult);

		int result = 0;
		int totalFactor = 11; // 총 만족도 조사 요건 ex) 지역, ..., 낚시 가능 여부
		int factorSum = 1; // 사용자 요청 지역과 일치한다는 전제로 +1로 시작

		factorSum = userResult.getEnableOps().contains("운영 가능") ? factorSum++ : factorSum;

		factorSum += userResult.getIsElectricity();
		factorSum += userResult.getIsWifi();
		factorSum += userResult.getIsCampfire();
		factorSum += userResult.getIsHeater();
		factorSum += userResult.getIsPool();
		factorSum += userResult.getIsPlayground();
		factorSum += userResult.getIsTrail();
		factorSum += userResult.getIsMaritimeLeisure();
		factorSum += userResult.getIsFishing();

		log.info("factorSum : {}", factorSum);
		log.info("totalFactor : {}", totalFactor);

		result = (int)((float)factorSum / (float)totalFactor * 100);

		log.info("만족도 : " + result);

		return result;
	}

	/**
	 * 개인별 캠프 추천
	 *
	 * @param userRequestVO
	 * @return
	 */
	public List<UserResultVO> predictCamp(UserRequestVO userRequestVO) {

		List<UserResultVO> recommList = new ArrayList<>();
		List<CampEntity> legacyCampList = (List<CampEntity>) campRepo.findAll();

		// log.info("기존 캠프장 리스트 수 : {}", legacyCampList.size());

		// 행선지 및 일정 우선적으로 검색 => 기타 부대/주변 시설 여부와 비교하여 추천 리스트 확보
		String dest = userRequestVO.getDestination();
		log.info("행선 예정지 : {}", dest);

//		long nameless = legacyCampList.stream()
//							.filter(x -> x.getSigugunName() != null && x.getSigugunName().equals("")).count();
//
//		log.info("시구군명이 없는 캠핑장 수 : {}", nameless);

		// filter 메서드에서 null 값 점검 주의 !
//		legacyCampList.stream()
//					  .filter(x -> (x.getSigugunName() != null && (x.getSidoName().contains(dest) || x.getSigugunName().contains(dest))))
//					  .toList()
//					  .forEach(x -> { log.info("{}", x); });

		// filter 메서드에서 null 값 점검 주의 !
		legacyCampList = legacyCampList.stream()
									   .filter(x -> (x.getSigugunName() != null && (x.getSidoName().contains(dest) || x.getSigugunName().contains(dest))))
									   .toList();

		UserResultVO userResult = null;

		// 나머지 요구사항 반영 비교
		for (CampEntity campEntity : legacyCampList) {

			log.info("for문 진입");

			userResult = new UserResultVO();

			// 행선 시기 가능 여부
			boolean enableOps = this.checkEnableSeason(campEntity, userRequestVO.getTime());

			String opsMsg = userRequestVO.getTime().format(DateTimeFormatter.ofPattern("YYYY년 MM월 dd일"))
						  + (enableOps == true ? "운영 가능" : "운영 불가");

			userResult.setEnableOps(opsMsg);

			// 부대/주변 시설 여부 점검
			userResult.setIsElectricity(campEntity.getFacilElectricity().equals("전기 사용가능") ? 1 : 0);
			userResult.setIsWifi(campEntity.getFacilWifi().equals("wifi 사용가능") ? 1 : 0);
			userResult.setIsCampfire(campEntity.getFacilCampfire().equals("장작판매") ? 1 : 0);
			userResult.setIsHeater(campEntity.getFacilHotWater().equals("온수 사용가능") ? 1 : 0);
			userResult.setIsPool(campEntity.getFacilPool().equals("물놀이장 보유") ? 1 : 0);
			userResult.setIsPlayground(campEntity.getFacilPlayground().equals("놀이터 보유") ? 1 : 0);
			userResult.setIsTrail(campEntity.getFacilTrail().equals("산책로 있음") ? 1 : 0);
			userResult.setIsMaritimeLeisure(campEntity.getSurrFacilMaritimeLeisure().equals("시설 주변 물놀이(수상레저) 있음") ? 1 : 0);
			userResult.setIsFishing(campEntity.getSurrFacilFishing().equals("낚시 시설 있음") ? 1 : 0);

			// 기타 스펙
			userResult.setCampId(campEntity.getId());
			userResult.setCampName(campEntity.getCampName());
			userResult.setUserId(userRequestVO.getUserId());
			userResult.setDestination(userRequestVO.getDestination());

			// 주소/위도/경도
			// 간혹 주소(road_address) 정보가 없으면서
			// 다른 주소 정보(jibun_address, eupmyundong_name, bunji_name, ry_name)는 있는 캠핑장이 있음
			// ex) 몽산포오션캠핑장

			userResult.setRoadAddress(campEntity.getRoadAddress());
			userResult.setJibunAddress(campEntity.getJibunAddress());
			userResult.setLatitude(campEntity.getLatitude());
			userResult.setLongitude(campEntity.getLongitude());

			// 사용자의 요구사항에 대한 만족도(충족도) 계산
			int satisfaction = this.calcSatisfaction(userResult);

			userResult.setSatisfaction(satisfaction);

			recommList.add(userResult);

			log.info("for문 next");

		} // for

		log.info("recommList size : {}", recommList.size());

		return recommList;
	} //

	/**
	 * 개인별 캠프 추천 (네이버 별점 및 긍정/부정지수 포함)
	 * 0522 leee response.data에서 불러오는 정보들. 여기에서 img를 추가해야 이미지 이름을 불러올 수 있음. 
	 * @param userRequestVO2
	 * @return
	 */
	public List<UserResultVO2> predictCampWithNaver(UserRequestVO userRequestVO) {

		List<UserResultVO2> recommList = new ArrayList<>();
		List<CampEntity> legacyCampList = (List<CampEntity>) campRepo.findAll();

		// 행선지 및 일정 우선적으로 검색 => 기타 부대/주변 시설 여부와 비교하여 추천 리스트 확보
		String dest = userRequestVO.getDestination();
		log.info("행선 예정지2 : {}", dest);

		// filter 메서드에서 null 값 점검 주의 !
		legacyCampList = legacyCampList.stream()
									   .filter(x -> (x.getSigugunName() != null && (x.getSidoName().contains(dest) || x.getSigugunName().contains(dest))))
									   .toList();

		UserResultVO2 userResult = null;

		// 나머지 요구사항 반영 비교
		for (CampEntity campEntity : legacyCampList) {

			log.info("for문 진입");

			userResult = new UserResultVO2();

			// 행선 시기 가능 여부
			boolean enableOps = this.checkEnableSeason(campEntity, userRequestVO.getTime());

			String opsMsg = userRequestVO.getTime().format(DateTimeFormatter.ofPattern("YYYY년 MM월 dd일"))
						  + (enableOps == true ? "운영 가능" : "운영 불가");

			userResult.setEnableOps(opsMsg);

			// 부대/주변 시설 여부 점검
			userResult.setIsElectricity(campEntity.getFacilElectricity().equals("전기 사용가능") ? 1 : 0);
			userResult.setIsWifi(campEntity.getFacilWifi().equals("wifi 사용가능") ? 1 : 0);
			userResult.setIsCampfire(campEntity.getFacilCampfire().equals("장작판매") ? 1 : 0);
			userResult.setIsHeater(campEntity.getFacilHotWater().equals("온수 사용가능") ? 1 : 0);
			userResult.setIsPool(campEntity.getFacilPool().equals("물놀이장 보유") ? 1 : 0);
			userResult.setIsPlayground(campEntity.getFacilPlayground().equals("놀이터 보유") ? 1 : 0);
			userResult.setIsTrail(campEntity.getFacilTrail().equals("산책로 있음") ? 1 : 0);
			userResult.setIsMaritimeLeisure(campEntity.getSurrFacilMaritimeLeisure().equals("시설 주변 물놀이(수상레저) 있음") ? 1 : 0);
			userResult.setIsFishing(campEntity.getSurrFacilFishing().equals("낚시 시설 있음") ? 1 : 0);

			// 기타 스펙
			userResult.setCampId(campEntity.getId());
			userResult.setCampName(campEntity.getCampName());
			userResult.setUserId(userRequestVO.getUserId());
			userResult.setDestination(userRequestVO.getDestination());
			
			// 0522 leee 추가 이미지이름을 불러와서 보여주기 위해서 추가함.  
			userResult.setCampImg(campEntity.getCampImg());
			userResult.setPhone(campEntity.getPhone());
			userResult.setFacilDetail(campEntity.getFacilDetail());
			userResult.setFacilCharacteristics(campEntity.getFacilCharacteristics());

			userResult.setSidoName(campEntity.getSidoName());
			userResult.setSigugunName(campEntity.getSigugunName());
			userResult.setSubTitle(campEntity.getSubTitle());

			// 0524 leee 추가 
			
			userResult.setWeekdayOpStatus(campEntity.getWeekdayOpStatus());
			userResult.setWeekendOpStatus(campEntity.getWeekendOpStatus());
			userResult.setHomepage(campEntity.getHomepage());
			userResult.setCate3(campEntity.getCate3());
			
			
			//0523 leee 추가 상세이미지 3개 불러옴. 
			List<String> campImgsName = campImgs(campEntity.getId());
			
			log.info("campImgsName확인:" +campImgsName);
			
			if(campImgsName.isEmpty() == false) {
				
				try {
					
					userResult.setImgName1(campImgsName.get(0).trim());
					userResult.setImgName2(campImgsName.get(1).trim());
					userResult.setImgName3(campImgsName.get(2).trim());
					
					log.info("여러개 이미지 확인:" + userResult.getImgName1());
					log.info("여러개 이미지 확인:" + userResult.getImgName2());
					log.info("여러개 이미지 확인:" + userResult.getImgName3());
					
				} catch (Exception e) {
					log.info("해당 캠핑장의 상세 이미지가 없습니다. ");
				}
			} 

			// 주소/위도/경도
			// 간혹 주소(road_address) 정보가 없으면서
			// 다른 주소 정보(jibun_address, eupmyundong_name, bunji_name, ry_name)는 있는 캠핑장이 있음
			// ex) 몽산포오션캠핑장

			userResult.setRoadAddress(campEntity.getRoadAddress());
			userResult.setJibunAddress(campEntity.getJibunAddress());
			userResult.setLatitude(campEntity.getLatitude());
			userResult.setLongitude(campEntity.getLongitude());

			// 사용자의 요구사항에 대한 만족도(충족도) 계산
			int satisfaction = this.calcSatisfaction(userResult);
			userResult.setSatisfaction(satisfaction);

			// 네이버 별점 및 긍정/부정지수 반영
			List<CampReviewVO> reviewList = campReviewDAO.findAllByCampName(campEntity.getCampName());

			log.info("reviewList 크기 : {}", reviewList.size());

			String avgRating = ""; // 네이버 별점
			int positiveDegree = 0; // 긍정 지수
			int negativeDegree = 0; // 부정 지수

			if (reviewList.size() > 0) {

				avgRating = (reviewList.get(0).getAvgRating() == null || reviewList.get(0).getAvgRating().equals("")) ?
						    "별점정보 없음" : reviewList.get(0).getAvgRating();
				positiveDegree = (int)reviewList.stream().filter(x->x.getPredict() == 1).count();
				negativeDegree = (int)reviewList.stream().filter(x->x.getPredict() == 0).count();

			} else {

				avgRating = "별점정보 없음";
				positiveDegree = 0;
				negativeDegree = 0;
			}

			userResult.setAvgRating(avgRating);

			log.info("긍정 지수 : {}", positiveDegree);
			log.info("부정 지수 : {}", negativeDegree);

			userResult.setReviewPositive(positiveDegree + "");
			userResult.setReviewNegative(negativeDegree + "");
			//userResult.setReviews(reviewList.get(0).getReviews());

			log.info("추천 캠핑장 : {}", userResult);

			recommList.add(userResult);

			log.info("for문 next");

		} // for

		log.info("recommList size : {}", recommList.size());

		return recommList;
	} //
	
	public List<String> campImgs(int id) {

		log.info("campImgslist id 확인 : " + id);
		List<String> campImgslist = new ArrayList<>();
		
		String imgs = campImgsRepo.findByCampImgs(id);
		
		if (imgs != null) {
			campImgslist = Arrays.asList(imgs.split("\\,"));
			log.info("campImgslist 확인 : " + campImgslist);
	    } 
		
		return campImgslist;
	}

	//0526 leee 삭제
	@Transactional
	public void deleteReservation(int id) {
		campDAO.deleteReservation(id);
	}
	
} //