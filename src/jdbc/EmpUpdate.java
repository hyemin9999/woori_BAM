package jdbc;

import java.sql.*;

public class EmpUpdate {
	public static void main(String[] args) {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "scott";
		String password = "tiger";

		// 인상 ==> sql * 1.1, 인하 == 원복 sql / 1.1
		String sql = "UPDATE emp SET sal = sal * 1.1 WHERE deptno = ?";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(url, user, password);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 20); // 부서번호 20번 사원들 급여 인상

			int result = pstmt.executeUpdate();
			System.out.println(result + "건의 급여가 인상되었습니다.");

			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
