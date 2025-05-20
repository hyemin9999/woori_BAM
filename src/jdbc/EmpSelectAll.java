package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class EmpSelectAll {
	public static void main(String[] args) {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "scott";
		String password = "tiger";

		String sql = "SELECT empno, ename, job, sal FROM emp";

		try {
			// 오라클 드라이버 로딩 -- 버전이 올라가면 생략 가능.
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(url, user, password);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			System.out.println("사번\t이름\t직무\t급여");
			while (rs.next()) {
				int empno = rs.getInt("empno");
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				double sal = rs.getDouble("sal");

				System.out.printf("%d\t%s\t%s\t%.2f%n", empno, ename, job, sal);
			}

			// 빠져나갈떄 close()
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}