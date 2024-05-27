package com.javateam.campProject;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.javateam.campProject.dao.FindByEmailTest;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class DateTest {
	
	@Test
	public void test() {
		
		Date date = new Date(System.currentTimeMillis());
		
	}

}
