
package com.woori.BAM;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	/** 명령어 종류 */
	public static class Commands { // static 특징, 학습내용을 기억하세요.
		/** 게시글 목록 */
		static String list = "article list";
		/** 게시글 작성 */
		static String write = "article write";
		/** 게시글 보기 */
		static String detail = "article detail";
		/** 게시글 수정 */
		static String edit = "article modify";
		/** 게시글 삭제 */
		static String delete = "article delete";
		/** 프로그램 종료 */
		static String exit = "exit";
	}

	/** 출력할 메세지 종류 */
	public static class Messages {
		/** 존재하는 게시글이 없습니다. */
		static String noAticle = "존재하는 게시글이 없습니다.";
		/** 게시글 번호를 입력해 주세요. */
		static String noAticleIndex = "게시글 번호를 입력해 주세요.";
		/** 존재하지 않는 명령어 입니다. */
		static String wrongCmd = "존재하지 않는 명령어 입니다.";
	}

	public static void main(String[] args) {

		// 게시글 배열 변수 선언
		// ArrayList 자료구조로 인덱스를 가지고 객체수는 제한이 없음.
		// List<Article> ==> 인터페이스, articles객체 변수명
		// ArrayList<E> 는 implements(구현) List(interface)
		List<Article> articles = new ArrayList<Article>();

		int lastArticleId = 0;// 등록된 게시글 번호

		System.out.println("== 프로그램 시작 ==");

		System.out.println("명령어 종류) " + Commands.list + " : 게시글 목록 \n" + Commands.write + " : 게시글 작성 \n"
				+ Commands.detail + " 번호 : 게시글 상세보기\n" + Commands.edit + " 번호 : 게시글 수정\n" + Commands.delete
				+ " 번호 : 게시글 삭제\n" + Commands.exit + " : 종료\n" + "게시글 번호는 숫자를 입력 바랍니다.\n");

		Scanner sc = new Scanner(System.in);

		while (true) {

			System.out.printf("명령어) ");
			String cmd = sc.nextLine().trim();

			if (cmd.equals("exit") || cmd.equals("0")) { // 종료일때
				break; // while문 빠져나감 ==> 프로그램 종료
			}

			if (cmd.replace("	", "").replace(" ", "").length() == 0) {// 입력된 값이 없을때, 공백과 탭키 없애기

				System.out.println("명령어를 입력해 주세요");
				continue;// 현재단계 중단 후 반복문 실행
			}

			if (cmd.startsWith(Commands.detail)) {// 게시글 상세보기

				Article article = getArticle(articles, cmd); // 중복되는 작업, 메소드 작업.

				if (article == null) {
					continue;
				}

				System.out.printf("번호 : %d\n", article.id);
				System.out.printf("날짜 : %s\n", article.update);
				System.out.printf("제목 : %s\n", article.title);
				System.out.printf("내용 : %s\n", article.body);

			} else if (cmd.startsWith(Commands.delete)) { // 게시글 삭제

				Article article = getArticle(articles, cmd);

				if (article == null) {
					continue;
				}

				articles.remove(article); // 검색한 게시글을 목록에서 삭제함

				System.out.printf("%d번의 게시글이 삭제되었습니다.\n", article.id);

			} else if (cmd.startsWith(Commands.edit)) { // 게시글 수정

				Article article = getArticle(articles, cmd);

				if (article == null) {
					continue;
				}

				// 입력받은 항목으로 게시글 재정의
				article = setArticle(article, sc, 0);
				System.out.printf("%d번글이 수정되었습니다.\n", article.id);

			} else if (cmd.equals(Commands.write)) { // 게시글 작성

				// 입력받은 항목으로 게시글 생성및 초기화
				Article article = setArticle(null, sc, ++lastArticleId);
				articles.add(article);
				System.out.printf("%d번글이 생성되었습니다.\n", lastArticleId);

			} else if (cmd.equals(Commands.list)) { // 게시글 목록

				// 게시글 목록이 없을때를 먼저 체크하는게 좋다.
				int articlesCnt = articles.size();

				if (articlesCnt == 0) { // 게시글 목록이 없을때를 의미
					System.out.println(Messages.noAticle);
					continue;
				}

				System.out.printf("%d개의 게시글이 있습니다.\n", articlesCnt);
				System.out.println("번호	|	제목	|	내용	|	날짜");

				// 저장된 게시글 목록 출력 ==> 최신글이 상단에 위치하도록 역순
				for (int i = articlesCnt - 1; i >= 0; i--) {

					Article article = articles.get(i);
					System.out.printf("%d	|	%s	|	%s	|	%s\n", article.id, article.title, article.body,
							article.date);
				}

			} else { // 명령어 외 다른것을 입력했을때
				System.out.println(Messages.wrongCmd);
			}
		}

		sc.close();
		System.out.println("== 프로그램 종료 ==");

	}

	/**
	 * 게시글 목록에서 입력받은 id값으로 검색 후 반환하는 함수</br>
	 * 입력받은 명령어에서 게시글 번호를 검색한다</br>
	 * 명령어 번호 형식일때 사용되는 함수
	 * 
	 * @param articles = 게시글 목록
	 * @param cmd      = 명령어
	 * @return 검색한 게시글
	 */
	public static Article getArticle(List<Article> articles, String cmd) { // static 메소드 특징, 학습내용을 기억하세요.

		Article article = null;

		try {

			String[] _cmd = cmd.split(" ");

			if (_cmd.length == 2) { // 명령어만 입력되었을때

				String tmp = null;

				// 명령어 '번호' 형식일때 명령어* 식으로 뒤에 뭐가 있을때 문구처리
				if (cmd.startsWith(Commands.detail)) {
					tmp = cmd.replace(Commands.detail, "");
				} else if (cmd.startsWith(Commands.delete)) {
					tmp = cmd.replace(Commands.delete, "");

				} else if (cmd.startsWith(Commands.edit)) {
					tmp = cmd.replace(Commands.edit, "");
				}

				if (tmp.length() != 0) {
					System.out.println(Messages.wrongCmd);
				}
			} else if (_cmd.length > 2) { // 명령어 뒤에 게시글 번호가 있으면

				int id = Integer.parseInt(_cmd[2]);

				for (Article item : articles) { // 게시글 목록에서 번호가 동일한 게시글 검색
					if (item.id == id) {
						article = item; // article은 객체이기때문에 주소를 복사한다.
						break; // 찾으면 for문 종료 다음코드 실행
					}
				}

				if (article == null) { // 검색된 게시글이 없으면
					System.out.println(Messages.noAticle);
				}

			} else { // 명령어 뒤에 게시글 번호가 입력이 안됐으면
				System.out.println(Messages.noAticleIndex);
			}

		} catch (NumberFormatException e) { // 입력된 게시글 번호가 숫자로 변환이 안되면 발생하는 예외처리
			System.out.println("숫자형식의 게시글 번호를 입력해 주세요.");
		} catch (Exception e) {
			System.out.println("오류가 발했습니다. : " + e.toString());
		}

		return article;
	}

	/**
	 * 입력받은 게시글을 정의 또는 재정의 해주는 함수
	 * 
	 * @param article       = 게시글 (null이면 글쓰기)
	 * @param sc            = 입력용 Scanner 변수
	 * @param lastArticleId = 글쓰기일때 게시글 마지막번호
	 */
	public static Article setArticle(Article article, Scanner sc, int lastArticleId) {

		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();

		if (article == null) { // 새로만들고 초기화
			article = new Article(lastArticleId, title, body);
		} else { // 입력받은값 재정의 넘겨받은 article이객체이기 때문에 해당 객체의 정보를 수정하면, 목록에서도 수정이됨.
			article.updateArticle(title, body);
		}

		return article;
	}
}

/**
 * 게시글
 */
class Article {
	/** 번호 */
	int id = 0;
	/** 제목 */
	String title = null;
	/** 내용 */
	String body = null;
	/** 등록 날짜 */
	String date = null;
	/** 수정 날짜 */
	String update = null;

	/**
	 * 게시글 생성자
	 * 
	 * @param _id    = 번호
	 * @param _title = 제목
	 * @param _body  = 내용
	 */
	Article(int _id, String _title, String _body) {
		id = _id;
		title = _title;
		body = _body;

		// 현재 날짜/시간
		LocalDateTime now = LocalDateTime.now(); // 현재 날짜/시간 출력 "yyyy-MM-dd HH:mm:ss.SSS"
		// 처음 게시글 등록할때 등록 날짜랑 수정 날짜를 동일하게 넣는다.
		date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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

		LocalDateTime now = LocalDateTime.now(); // 현재 날짜/시간 출력
		update = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
}
