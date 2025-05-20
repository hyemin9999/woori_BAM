package jdbc;

import java.sql.*;

public class EmpPreparedSelect {
	public static void main(String[] args) {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "scott";
		String password = "tiger";

		String sql = "SELECT ename, job, sal FROM emp WHERE sal >= ?";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection(url, user, password);

			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, 2000); // ? 에 들어갈 값 설정

			ResultSet rs = pstmt.executeQuery();

			System.out.println("이름\t직무\t급여");

			// while문이 필요할경우 ==> ResultSet
			while (rs.next()) {
				String ename = rs.getString("ename");
				String job = rs.getString("job");
				double sal = rs.getDouble("sal");

				System.out.printf("%s\t%s\t%.2f%n", ename, job, sal);
			}

			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
