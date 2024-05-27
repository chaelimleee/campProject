package com.javateam.campProject.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class ReservationVO {
	
	/**	예약 id */
	private int id;

	/**	캠프 id */
	private int campId;

	/**	캠프장 이름 */
	private String campName;

	/**	예약자 이메일 */
	private String email;

	/**	예약자 이름*/
	private String userName;

	/**	예약날짜(당일)*/
	private Date regDate;

	/**	예약여부*/
	private int yesNo;
	

}
