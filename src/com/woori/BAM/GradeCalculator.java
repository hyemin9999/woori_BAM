package com.woori.BAM;

import java.util.Scanner;

public class GradeCalculator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);

		System.out.print("학생 이름 : ");
		String name = sc.nextLine();
		System.out.print("점수 : ");
		int score = sc.nextInt();
		String grade;

		switch (score / 10) {
		case 10:
		case 9:
			grade = "A";
			break;
		case 8:
			grade = "B";
			break;
		case 7:
			grade = "C";
			break;
		case 6:
			grade = "D";
			break;
		default:
			grade = "F";
			break;
		}

		sc.close();
		System.out.printf("%s 학생의 점수는 %s 등급입니다.\n", name, grade);
	}

}
