package zad1.Controller;
import zad1.Model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDatabase {

    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://localhost/books?serverTimezone=UTC";
    private String uid = "root";
    private String pwd = "GeneratoryPassword";
    private Connection connection;
    private Statement statement;

    public BookDatabase() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, uid, pwd);
            statement = connection.createStatement();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }
}
