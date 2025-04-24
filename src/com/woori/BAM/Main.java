
package com.woori.BAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		// 게시글 배열 변수 선언
		// ArrayList 자료구조로 인덱스를 가지고 객체주는 제한이 없음.
		List<Article> aList = new ArrayList<Article>();

		int index = 0;
		System.out.println("== 프로그램 시작 ==");
		System.out.println("명령어 종류) article list, article write : 게시글 작성, exit : 종료");

		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine().trim();

			if (cmd.equals("exit")) {
				break; // while문 빠져나감
			}

			// 아무것도 입력 안했을때를 의미
			if (cmd.replace("	", "").replace(" ", "").length() == 0) {
				System.out.println("명령어를 입력해 주세요");
				continue;// 현재단계 중단 및 while문 처음부터 시작
			}

			if (cmd.equals("article list")) {// 게시글 목록
				if (aList.size() == 0) { // 게시글 목록이 없을때를 의미
					System.out.println("게시글이 없습니다.");
				} else {
					System.out.printf("%d개의 게시글이 있습니다.\n", aList.size());

					// 저장된 게시글 확인
					for (int i = 0; i < aList.size(); i++) {
						System.out.printf("%d %s \"%s\"\n", aList.get(i).index, aList.get(i).title, aList.get(i).body);

					}

				}

			} else if (cmd.equals("article write")) { // 글 생성
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();

				// 크기는 1 이다. 이전의 번호, 제목, 내용은 크기가 3
//				Article aitem = new Article(++index,title,body); //새성자 오버로딩을 통해 각 값을 생성자에 넘김
//
//				// 최적화 작업. Article생성자 오버로딩
////				aitem.index = ++index;
////				aitem.title = title;
////				aitem.body = body;
//
//				aList.add(aitem);

				// 최적화 2 생성과 add를 합친다 - 두줄을 한줄로 // 사용자 마음대로 2줄로 하던지 1줄로 하던지
				aList.add(new Article(++index, title, body));

				System.out.printf("%d번글이 생성되었습니다.\n", index);

			} else { // 아무거나 입력했을때를 의미
				System.out.println("존재하지 않는 명령어 입니다.");
			}

		}
		System.out.println("== 프로그램 종료 ==");
		sc.close();

	}
}

class Article {
	int index = 0;
	// 제목
	String title = null;
	// 내용
	String body = null;

	Article(int _index, String _title, String _body) {
		index = _index;
		title = _title;
		body = _body;
	}
}
