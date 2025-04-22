package com.woori.BAM;

import java.util.Scanner; //표준라이러리에서 import

public class Main {
	// 전역(Global)변수/지역(Local)변수
	public static void main(String[] args) {
		System.out.println("== 프로그램 시작 ==");

		// System : class, in : 변수,필드(field), final static : 상수
		// Scanner(System.in) : 생성자
		Scanner sc = new Scanner(System.in); // while문 위로 옮겨서 자원낭비를 줄임 = 최적화, 리팩토링

		while (true) {
			System.out.printf("cmd) ");
			// sc.next() String값을 반환하는 메서드
//			System.out.println(sc.next());

			String cmd = sc.nextLine(); // cmd 변수 == > 재사용 하기 위해
			// . : 멤버접근 연산자
			System.out.println("명령어) " + cmd);
			// 무한반복 구간을 나가기 위한 조건문과 break를 사용
			// 종료값을 입력받으면
			if (cmd.equals("exit")) {// .eauals는 참조형 객체의 값이 같은지 확인할수있다
				break;
			}
		}

		// 종료버튼을 누를때 종료되도록 하려면
		// 종료버튼을 누르기전까지 무한반복을 돌린다
		System.out.println("== 프로그램 종료 ==");

		sc.close(); // 안닫으면 쓰레기값이 됨 그걸 처리해주는게 "GC : 가비지 콜렉터"

	}

}
