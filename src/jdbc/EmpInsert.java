package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

//import java.sql.*;

public class EmpInsert {
	public static void main(String[] args) {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "scott";
		String password = "tiger";

		// 값이 변하는 곳에는 ?를 작성해놓고 나중에 컨버팅함.==> PreparedStatement.setXXX()
		// 보안에 좋음.. 인젝션??==> sql 쿼리문을 채가서 본인에게 필요한 값으로처리하는거
		// SYSDATE ==> 현재 날짜와 시간. ==> 오라클 데이터 베이스 서버의....
		// java LocalDateTime ==> java 표준 날짜 및 시간
		String sql = "INSERT INTO emp(empno, ename, job, mgr, hiredate, sal, comm, deptno) "
				+ "VALUES (?, ?, ?, ?, SYSDATE, ?, ?, ?)";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(url, user, password);

//			conn.setAutoCommit(false);

			// setXX() ==> 저장... setter/getter
			// hiredate 열의 데이터는 SYSDATE가 들어가므로 필요한 값은 7개.
			// SQL 문을 미리 준비하고, 파라미터만 바인딩하여 실행하는 객체.
			PreparedStatement pstmt = conn.prepareStatement(sql);
			// 하단의 내용으로 sql 문이 컨버팅됨. ==> 스스로 바인딩이 됨.
			pstmt.setInt(1, 9001); // empno
			pstmt.setString(2, "JUNHO"); // ename
			pstmt.setString(3, "ANALYST"); // job
			pstmt.setInt(4, 7566); // mgr
			pstmt.setDouble(5, 3200.0); // sal
			pstmt.setDouble(6, 0.0); // comm
			pstmt.setInt(7, 20); // deptno

			int result = pstmt.executeUpdate();
			System.out.println(result + "건이 입력되었습니다.");

//			conn.commit();
//			System.out.println("변경 사항이 커밋되었습니다.");

			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
