import java.sql.Connection;
import java.sql.DriverManager;

public class DB {

    public static Connection connect() throws Exception {
        String url = "jdbc:postgresql://localhost:5432/digital_library";
        String user = "postgres";
        String password = "0000";

        return DriverManager.getConnection(url, user, password);
    }
}