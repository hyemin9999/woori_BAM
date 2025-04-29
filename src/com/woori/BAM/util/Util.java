package com.woori.BAM.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
	/**
	 * 현재 날짜 시간을 반환하는 함수
	 */
	public static String getDateTimeStr() {
		LocalDateTime now = LocalDateTime.now(); // 현재 날짜/시간 출력 "yyyy-MM-dd HH:mm:ss.SSS"
		return now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}