package com.woori.BAM;
import java.util.Scanner; //Scanner사용을 위한 import

public class Main3 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); // 사용자에게 입력받을때 사용하는 객체

		System.out.println("====== 프로그램 시작 ======\n");
		System.out.println("등급 기준은 아래와 같습니다.\n90점이상 A, 80점대 B, 이하 C\n");
		System.out.print("입력할 학생의 총 인원 수 를 입력해주세요 : "); // 사용자에게 입력받을 학생의 수를 물어봅니다.

		int count = sc.nextInt(); // 입력받은 학생의 수를 변수에 저장합니다.

		// 입력받은 학생의 이름과 점수, 그리고 점수를 통해 계산된 등급을 저장할 배열변수 선언
		// 입력받은 총 인원의 수만큼 만든다.
		String[] names = new String[count]; // 입력받은 학생의 수만큼의 이름 배열
		int[] scores = new int[count]; // 입력받은 학생의 수만큼의 점수 배열
		String[] grades = new String[count]; // 입력받은 학생의 점수로 계산된 등급 배열

		// for문을 통해 입력받은 총 학생의 인원수 만큼 이름과 점수를 입력받고 입력된 점수를 기반으로 등급을 설정합니다.
		for (int i = 0; i < count; i++) {
			System.out.printf("%d 번째 학생의 이름을 입력해주세요 : ", i + 1); // for문에서 i가 0부터 시작하므로 출력되는 값은 i+1로 처리
			String name = sc.next(); // 입력받은 학생이 이름을 저장할 변수
			System.out.printf("%d 번째 학생의 점수를 입력해주세요 : ", i + 1); // for문에서 i가 0부터 시작하므로 출력되는 값은 i+1로 처리
			int score = sc.nextInt(); // 입력받은 학생의 점수를 저장할 변수

			scores[i] = score; // 입력된 순서대로 점수를 배열에 저장
			names[i] = name; // 입력된 순서대로 이름을 배열에 저장

			String grade = ""; // 입력된 점수를 기반으로 등급을 설정해서 저장할 등급변수

			// 90점 이상 A, 80~89점 B, 그외 C등급을 구한다.
			switch (score / 10) {
			case 10:
			case 9: // 90점 이상 A등급
				grade = "A";
				break;
			case 8: // 90점 미만 80점 이상 B등급
				grade = "B";
				break;
			default: // 그외점수 C등급
				grade = "C";
				break;
			}
			grades[i] = grade;// 점수를 기반으로 산정된 등급을 입력된 순서대로 배열에 저장
		}

		System.out.println("\n====== 입력된 " + count + "명의 이름, 점수, 등급을 출력 ======\n");
		// 입력된 모든 학생의 이름과 점수,등급을 표형태로 출력
		System.out.println("이름		점수		등급"); // 표형태로 출력하기 위한 title
		for (int i = 0; i < count; i++) { // 입력한 총 인원수만큼 실행되는 for문
			System.out.printf("%s		%d		%s\n", names[i], scores[i], grades[i]); // 순서대로 각 배열에서 항목들을 출력
		}

		sc.close(); // 사용한 Sanner객체를 닫아줍니다.
		System.out.println("\n====== 프로그램 종료 ======");
	}
}
