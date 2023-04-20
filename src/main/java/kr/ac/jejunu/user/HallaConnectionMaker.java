package kr.ac.jejunu.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HallaConnectionMaker implements ConnectionMaker {
    @Override
    public Connection getConnection() throws ClassNotFoundException, SQLException {
        //mysql
        //driver loading
        Class.forName("com.mysql.cj.jdbc.Driver");
        //connection
        // jeju -> halla로 수정했다고 가정
        return DriverManager.getConnection("jdbc:mysql://localhost/Jeju?","jeju", "jejupw");
    }
}
