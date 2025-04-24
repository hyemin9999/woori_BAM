
package com.woori.BAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static String notAticleTxt = "존재하지않는 게시글 입니다.";
	public static String notAticleIndex = "입력된 게시글 번호가 없습니다.";

	public static void main(String[] args) {

		// 게시글 배열 변수 선언
		// ArrayList 자료구조로 인덱스를 가지고 객체주는 제한이 없음.
		List<Article> articles = new ArrayList<Article>();

		int index = 0;

//		ItemCheck itemChk = new ItemCheck().setItemChk(false, false);

		boolean listChk = false; // 게시글이 있는 게시글 목록인지 확인하는 용도
		boolean viewChk = false; // 게시글 보기인지 확인 하는 용도

		System.out.println("== 프로그램 시작 ==");

		System.out.println("명령어 종류) article list : 게시글 목록 \narticle write : 게시글 작성 \n"
				+ "article view \'번호\' : 입력한 번호의 게시글 내용 (\'\'제외, 숫자만 입력해주세요.)\n"
				+ "article delete \'번호\' : 입력한 번호의 게시글 삭제 (\'\'제외, 숫자만 입력해주세요.) \n" + "exit : 종료");

		Scanner sc = new Scanner(System.in);

		while (true) {

			System.out.printf("명령어) ");
			String cmd = sc.nextLine().trim();

			if (cmd.equals("exit") || cmd.equals("0")) { // 종료일때
				break; // while문 빠져나감 ==> 프로그램 종료
			}

			if (cmd.replace("	", "").replace(" ", "").length() == 0) {// 아무것도 입력 안했을때를 의미
				System.out.println("명령어를 입력해 주세요");

//				itemChk = itemChk.setItemChk(false, false);

				listChk = false; // 게시글 확인여부 초기화 false : 체크안함
				continue;// 현재단계 중단 및 반복문 실행
			}

			if (listChk == true && cmd.contains("article view")) { // 게시글 항목을 보기

//				itemChk = itemChk.setItemChk(false, true);
				listChk = false; // 게시글 보고 나서 게시글 목록으로 보내기 위한 체크
				getItemPrint(cmd, articles);

			} else if (listChk == true && cmd.contains("article delete")) { // 개시글 삭제

//				itemChk = itemChk.setItemChk(false, false);
				listChk = false; // 게시글 보고 나서 게시글 목록으로 보내기 위한 체크
				getItemPrint(cmd, articles);

			} else if (cmd.equals("article list")) {// 게시글 목록
				// 게시글 목록이 없을때를 먼저 체크하는게 좋다.
				if (articles.size() == 0) { // 게시글 목록이 없을때를 의미
					System.out.println("존재하는 게시글이 없습니다.");
//					itemChk = itemChk.setItemChk(false, false);
					listChk = false; // 게시글 확인여부 초기화 false : 체크안함
				} else {
					System.out.printf("%d개의 게시글이 있습니다.\n", articles.size());
					System.out.println("번호	|	제목");
					// 저장된 게시글 목록 출력 ==> 최신글이 상단에 위치하도록 역순
					for (int i = articles.size() - 1; i >= 0; i--) {
						System.out.printf("%d	|	%s\n", articles.get(i).index, articles.get(i).title);
					}

//					itemChk = itemChk.setItemChk(true, false);
					listChk = true; // 게시글 목록 확인 여부 체크 true : 체크함
				}

			} else if (cmd.equals("article write")) { // 글 생성
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();

				Article aitem = new Article(++index, title, body); // 새성자 오버로딩을 통해 각 값을 생성자에 넘김
				articles.add(aitem);

				System.out.printf("%d번글이 생성되었습니다.\n", index);
//				itemChk = itemChk.setItemChk(false, false);
				listChk = false; // 게시글 확인여부 초기화 false : 체크안함
			} else { // 아무거나 입력했을때를 의미
				System.out.println("존재하지 않는 명령어 입니다.");
//				itemChk = itemChk.setItemChk(false, false);
				listChk = false; // 게시글 확인여부 초기화 false : 체크안함
			}
		}

		System.out.println("== 프로그램 종료 ==");
		sc.close();

	}

	/**
	 * 항목을 선택해서 출력하는 함수
	 * 
	 * @param cmd   = 입력받은 명령어
	 * @param aList = 넘겨받은 게시글 목록
	 */
	public static void getItemPrint(String cmd, List<Article> articles) {

		int num = articles.size() + 1; // 입력받은 게시글 번호가 게시글 목록의 갯수보다 클때 게시글 존재 하지않음을 처리하기 위한 변수

		if (cmd.contains("article view")) { // 게시글 보기 (article view 게시글 번호) 형식

			if (cmd.replace("article view", "").trim().length() != 0) {
				num = Integer.parseInt(cmd.replace("article view", "").trim());
			} else { // 번호입력이 안되어있을때
				System.out.println(notAticleIndex);
			}
		} else if (cmd.contains("article delete")) { // 게시글 삭제 (article delete 게시글 번호) 형식
			if (cmd.replace("article view", "").trim().length() != 0) {
				num = Integer.parseInt(cmd.replace("article view", "").trim());
			} else { // 번호입력이 안되어있을때
				System.out.println(notAticleIndex);
			}
		}

		if (num <= articles.size()) { // 존재하는 게시글 여부 확인 : 입력받은 게시글번호가 게시글 목록보다 작거나 같은지 확인
			// article은 articles.get()을 통해 받은 객체를 재사용하기위해
			Article article = articles.get(num - 1); // 해당 인덱스의 게시글 가져오기. 인덱스는 입력된 번호 - 1

			if (cmd.contains("article view")) { // 게시글 보기일때
				System.out.printf("%d번의 게시글입니다.\n", num);

				System.out.println("번호	|	제목	|	내용");
				System.out.printf("%d	|	%s	|	\"%s\"\n", article.index, article.title, article.body);

			} else if (cmd.contains("article delete")) { // 게시글 삭제일때
				articles.remove(num - 1);

				System.out.printf("%d번의 게시글이 삭제되었습니다.\n", num);
			}

		} else {
			System.out.println(notAticleTxt);
		}
	}
}

class Article {
	int index = 0;
	// 제목
	String title = null;
	// 내용
	String body = null;

	/**
	 * 게시글 생성자
	 * 
	 * @param _index = 번호
	 * @param _title = 제목
	 * @param _body  = 내용
	 */
	Article(int _index, String _title, String _body) {
		index = _index;
		title = _title;
		body = _body;
	}
}

/**
 * 지금 보고 있는게 무엇인지 체크해주는 객체
 * 
 * @param listChk = '게시글 목록' 체크 여부
 * @param viewChk = '게시글 보기' 체크 여부
 */
//class ItemCheck {
//	boolean listChk = false; // 게시글 목록부분인지 체크하는 용도
//	boolean viewChk = false; // 게시글 보기부분인지 체크하는 용도
//
//	public ItemCheck setItemChk(boolean _listChk, boolean _viewChk) {
//		listChk = _listChk;
//		viewChk = _viewChk;
//
//		System.out.println("게시글 목록 여부 : " + listChk + "\n게시글 보기 여부 : " + viewChk);
//
//		return this;
//	}
//}
