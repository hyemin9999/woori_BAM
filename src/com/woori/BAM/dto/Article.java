package com.woori.BAM.dto;

import com.woori.BAM.util.Util;

/**
 * 게시글
 */
public class Article {
	/**
	 * 추후 DB와 연결될때 private로 될수밖에없어서 dto패키지안에있는 클래스는 무조건 private,<br/>
	 * 또한 해당 전역변수를 사용하기위해 getter/setter를 사용한다.
	 */
	/** 번호 */
	private int id = 0;
	/** 제목 */
	private String title = null;
	/** 내용 */
	private String body = null;
	/** 등록 날짜 */
	private String date = null;
	/** 수정 날짜 */
	private String update = null;
	/** 조회수 */
	private int viewCnt = 0;

	/**
	 * getter/setter 카멜표기법으로 get'대문자''소문자'<br/>
	 * getter : read setter : 저장
	 */
	/** 번호 반환 */
	public int getId() {
		return id;
	}

	/** 번호 설정 */
	public void setId(int id) {
		this.id = id;
	}

	/** 제목 반환 */
	public String getTitle() {
		return title;
	}

	/** 제목 설정 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 내용 반환 */
	public String getBody() {
		return body;
	}

	/** 내용 설정 */
	public void setBody(String body) {
		this.body = body;
	}

	/** 등록날짜 반환 */
	public String getDate() {
		return date;
	}

	/** 등록날짜 설정 */
	public void setDate(String date) {
		this.date = date;
	}

	/** 수정날짜 반환 */
	public String getUpdate() {
		return update;
	}

	/** 수정날짜 설정 */
	public void setUpdate(String update) {
		this.update = update;
	}

	/** 조회수 반환 */
	public int getViewCnt() {
		return viewCnt;
	}

	/** 조회수 설정 */
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}

	/**
	 * 게시글 생성자
	 * 
	 * @param _id      = 번호
	 * @param _title   = 제목
	 * @param _body    = 내용
	 * @param _viewCnt = 조회수
	 */
	public Article(int _id, String _title, String _body, int _viewCnt) {
		id = _id;
		title = _title;
		body = _body;
		viewCnt = _viewCnt;

		// 처음 게시글 등록할때 등록 날짜랑 수정 날짜를 동일하게 넣는다.
		date = Util.getDateTimeStr();
		update = date;
	}

	/**
	 * 게시글 수정하기위해 입려받은 제목과 내용을 재정의하고 수정날짜도 재정의한다.
	 * 
	 * @param _title = 제목
	 * @param _body  = 내용
	 */
	public void updateArticle(String _title, String _body) {
		title = _title;
		body = _body;

		update = Util.getDateTimeStr();
	}

	/**
	 * 게시글 조회수 증감 메서드
	 */
	public void increaseViewCnt() {
		this.viewCnt++;
	}
}
