
package connection;


import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private BasicDataSource dataSource;
    private String host = "localhost";
    private String port = "8111";
    private String database = "mydatabase";
    private String username = "admin";
    private String password = "";


    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    private DatabaseConnection() {

    }

    public void connectToDatabase() throws SQLException {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.start();
    }

    public Connection createConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close(AutoCloseable... close) throws SQLException {
        try {
            for (AutoCloseable c : close) {
                if (c != null) {
                    c.close();
                }
            }
        } catch (Exception e) {
            throw new SQLException("Error on closing");
        }
    }
}
