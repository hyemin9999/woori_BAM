package jdbc;
import java.sql.*;

public class EmpSalOver2000 {
    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@localhost:1521:orcl";
        String user = "scott";
        String password = "tiger";

        String sql = "SELECT ename, job, sal FROM emp WHERE sal >= 2000";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("이름\t직무\t급여");
            while (rs.next()) {
                String ename = rs.getString("ename");
                String job = rs.getString("job");
                double sal = rs.getDouble("sal");

                System.out.printf("%s\t%s\t%.2f%n", ename, job, sal);
            }

            rs.close(); stmt.close(); conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
