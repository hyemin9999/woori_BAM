
package com.woori.BAM;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
	/**
	 * 존재하는 게시글이 없습니다
	 */
	public static String notAticleTxt = "존재하는 게시글이 없습니다.";
	/**
	 * 올바른 게시글 번호를 입력 바랍니다.
	 */
	public static String notAticleIndex = "올바른 게시글 번호를 입력 바랍니다.";

	/**
	 * 명령어 종류
	 * 
	 * @param list   = '게시글 목록'
	 * @param write  = '게시글 작성'
	 * @param detail = '게시글 보기'
	 * @param edit   = '게시글 수정'
	 * @param delete = '게시글 삭제'
	 * @param exit   = '프로그램 종료'
	 */
	public static class Commands {
		static String list = "article list";
		static String write = "article write";
		static String detail = "article detail";
		static String edit = "article edit";
		static String delete = "article delete";
		static String exit = "exit";
	}

	public static void main(String[] args) {

		// 게시글 배열 변수 선언
		// ArrayList 자료구조로 인덱스를 가지고 객체주는 제한이 없음.
		// List<Article> ==> 인터페이스, articles객체 변수명
		// ArrayList<E> 는 implements(구현) List(interface)
		List<Article> articles = new ArrayList<Article>();

		int lastArticleId = 0;// 등록된 게시글 번호
		int selectId = 0; // 상세보기 게시글 번호

		ItemCheck itemChk = new ItemCheck().setItemChk(false);

		System.out.println("== 프로그램 시작 ==");

		System.out.println("명령어 종류) article list : 게시글 목록 \narticle write : 게시글 작성 \n"
				+ "article detail \'번호\' : 입력한 번호의 게시글 상세보기 (\'\'제외, 숫자만 입력해주세요.)\n"
				+ "article delete \'번호\' : 입력한 번호의 게시글 삭제 (\'\'제외, 숫자만 입력해주세요.) \n" + "exit : 종료\n"
				+ "===============\n게시글 상세보기와 삭제는 게시글 목록 상태에서만 가능합니다.");

		Scanner sc = new Scanner(System.in);

		while (true) {

			System.out.printf("명령어) ");
			String cmd = sc.nextLine().trim();

			if (cmd.equals("exit") || cmd.equals("0")) { // 종료일때
				break; // while문 빠져나감 ==> 프로그램 종료
			}

			if (cmd.replace("	", "").replace(" ", "").length() == 0) {// 아무것도 입력 안했을때를 의미
				System.out.println("명령어를 입력해 주세요");

				itemChk = itemChk.setItemChk(false);
				continue;// 현재단계 중단 및 반복문 실행
			}

			if (itemChk.viewChk == false) { // 게시글 보기 상태가 아니면 게시글 index초기화
				selectId = 0;
			}

			if (itemChk.listChk == true && cmd.startsWith(Commands.detail.toString())) { // 게시글 항목을 보기

				int id = articles.size() + 1; // 입력받은 게시글 번호가 게시글 목록의 갯수보다 클때 게시글 존재 하지않음을 처리하기 위한 변수

				id = getId(cmd);

				Article article = null;

				if (id <= articles.size() && id != 0) { // 존재하는 게시글 여부 확인 : 입력받은 게시글번호가 게시글 목록보다 작거나 같은지 확인
					// article은 articles.get() 을 통해 받은 객체를 재사용하기 위해 저장 용도로 사용됨
					article = articles.get(id - 1); // 해당 인덱스의 게시글 가져오기. 인덱스는 입력된 번호 - 1
				}

				if (article == null) {
					System.out.println(notAticleTxt);
					continue;
				}

				System.out.printf("번호 : %d\n", article.id);
				System.out.printf("날짜 : %s\n", article.update);
				System.out.printf("제목 : %s\n", article.title);
				System.out.printf("내용 : %s\n", article.body);

				itemChk.setItemChk(false, true);

				// getItemPrint(cmd, articles, itemChk);

			} else if (itemChk.listChk == true && cmd.startsWith(Commands.delete.toString())) { // 게시글 삭제
				
				 getItemPrint(cmd, articles, itemChk);

			} else if (itemChk.listChk == true && cmd.startsWith(Commands.edit.toString())) { // 게시글 수정
				// 지금 선택된 게시글의 번호 필요
				selectId = getId(cmd);

				if (selectId != 0) {
					// 다시 제목 내용 입력받아서
					System.out.printf("제목 : ");
					String title = sc.nextLine();
					System.out.printf("내용 : ");
					String body = sc.nextLine();

					// 해당 게시글 번호 게시글에 수정하고 update날짜 현재날짜로 수정
					Article aitem = articles.get(selectId - 1);
					aitem.setArticle(title, body);

					System.out.printf("%d번글이 수정되었습니다.\n", selectId);
				} else {

					System.out.println(notAticleTxt);
				}
			} else if (cmd.equals(Commands.list.toString())) {// 게시글 목록
				// 게시글 목록이 없을때를 먼저 체크하는게 좋다.

				int articlesCnt = articles.size();

				if (articlesCnt == 0) { // 게시글 목록이 없을때를 의미
					System.out.println(notAticleTxt);
					itemChk = itemChk.setItemChk(false);

					continue;
				}

				System.out.printf("%d개의 게시글이 있습니다.\n", articlesCnt);
				System.out.println("번호	|	제목");
				// 저장된 게시글 목록 출력 ==> 최신글이 상단에 위치하도록 역순
				for (int i = articlesCnt - 1; i >= 0; i--) {

					Article article = articles.get(i);
					System.out.printf("%d	|	%s\n", article.id, article.title);
				}

				itemChk = itemChk.setItemChk(true, false);// 게시글 목록 확인 여부 체크 true : 체크함

			} else if (cmd.equals(Commands.write.toString())) { // 글 작성
				System.out.printf("제목 : ");
				String title = sc.nextLine();
				System.out.printf("내용 : ");
				String body = sc.nextLine();

				Article aitem = new Article(++lastArticleId, title, body); // 새성자 오버로딩을 통해 각 값을 생성자에 넘김
				articles.add(aitem);

				System.out.printf("%d번글이 생성되었습니다.\n", lastArticleId);
				itemChk = itemChk.setItemChk(false);// 게시글 확인여부 초기화 false : 체크안함
			} else { // 아무거나 입력했을때를 의미
				System.out.println("존재하지 않는 명령어 입니다.");
				itemChk = itemChk.setItemChk(false);// 게시글 확인여부 초기화 false : 체크안함
			}
		}

		System.out.println("== 프로그램 종료 ==");
		sc.close();

	}

	/**
	 * 항목을 선택해서 출력하는 함수
	 * 
	 * @param cmd      = 입력받은 명령어
	 * @param articles = 넘겨받은 게시글 목록
	 * @param _itemChk = 넘겨받은 게시글 목록/보기 여부
	 */
	public static void getItemPrint(String cmd, List<Article> articles, ItemCheck _itemChk) {

		int id = articles.size() + 1; // 입력받은 게시글 번호가 게시글 목록의 갯수보다 클때 게시글 존재 하지않음을 처리하기 위한 변수
		_itemChk.setItemChk(true, false);

		id = getId(cmd);

		if (id <= articles.size() && id != 0) { // 존재하는 게시글 여부 확인 : 입력받은 게시글번호가 게시글 목록보다 작거나 같은지 확인
			// article은 articles.get() 을 통해 받은 객체를 재사용하기 위해 저장 용도로 사용됨
			Article article = articles.get(id - 1); // 해당 인덱스의 게시글 가져오기. 인덱스는 입력된 번호 - 1

			if (cmd.startsWith(Commands.detail.toString())) { // 게시글 보기일때
				System.out.printf("번호 : %d\n", article.id);
				System.out.printf("날짜 : %s\n", article.update);
				System.out.printf("제목 : %s\n", article.title);
				System.out.printf("내용 : %s\n", article.body);

				_itemChk.setItemChk(false, true);

			} else if (cmd.startsWith(Commands.delete.toString())) { // 게시글 삭제일때
				articles.remove(id - 1);

				System.out.printf("%d번의 게시글이 삭제되었습니다.\n", id);
			}

		} else {
			System.out.println(notAticleTxt);
		}
	}

	/**
	 * 입력된 명령어에서 게시글 번호가 입력되었는지 체크후 번호 반환 반환값이 0이면 게시글이 없는것
	 * 
	 * @param cmd = 명령어
	 */
	public static int getId(String cmd) {
		int id = 0;

		String[] _cmd = cmd.split(" ");

		// 게시글 보기/삭제/수정 중 하나면 입력된 값이 숫자인지 확인 후 게시글 번호 반환
		if (_cmd.length > 2 && (cmd.startsWith(Commands.detail.toString()) || cmd.startsWith(Commands.delete.toString())
				|| cmd.startsWith(Commands.edit.toString()))) {

			if (_cmd[2] != null && isNumberic(_cmd[2])) {
				id = Integer.parseInt(_cmd[2]);
			}
//			else {
//				System.out.println(notAticleIndex);
//			}
		}
//		else {
//			System.out.println(notAticleIndex);
//		}

		return id;
	}

	/**
	 * 입력된 게시글 번호가 숫자인지 체크하는 함수
	 */
	public static boolean isNumberic(String str) {
		return str.matches("[+-]?\\d*(\\.\\d+)?");
	}
}

/**
 * 게시글 class
 * 
 * @param id    = 번호
 * @param title = 제목
 * @parem body = 내용
 * @param date   = 등록날짜
 * @param update = 수정날짜
 */
class Article {
	int id = 0;
	String title = null;
	String body = null;
	String date = null;
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
		LocalDateTime now = LocalDateTime.now(); // 현재 날짜/시간 출력
		date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
		update = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	}

	/**
	 * 게시글 수정하기위해 입려받은 제목과 내용을 재정의하고 수정날짜도 재정의한다.
	 * 
	 * @parma _title = 제목
	 * @param _body = 내용
	 */
	public void setArticle(String _title, String _body) {
		title = _title;
		body = _body;

		LocalDateTime now = LocalDateTime.now(); // 현재 날짜/시간 출력
		update = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
	}
}

/**
 * <br>
 * 지금 보고 있는게 무엇인지 체크해주는 객체</br>
 * 게시글 목록에서 게시글 수정을 하도록 수정하면서 viewChk사용안됨. <br>
 * 언젠가 사용할지도 모르니까 그냥 두겠음.</br>
 * 
 * @param listChk = '게시글 목록' 체크 여부
 * @param viewChk = '게시글 보기' 체크 여부
 */
class ItemCheck {
	boolean listChk = false; // 게시글 목록부분인지 체크하는 용도
	boolean viewChk = false; // 게시글 보기부분인지 체크하는 용도

	public ItemCheck setItemChk(boolean _listChk, boolean _viewChk) {
		listChk = _listChk;
		viewChk = _viewChk;

		return this;
	}

	/**
	 * listChk 과 viewChk이 동일한 값으로 설정하는 함수
	 */
	public ItemCheck setItemChk(boolean _Chk) {
		listChk = _Chk;
		viewChk = _Chk;

		return this;
	}
}
