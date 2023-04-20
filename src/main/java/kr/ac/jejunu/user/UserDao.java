package kr.ac.jejunu.user;

import java.sql.*;

public abstract class UserDao {

    public User findId(Long id) throws ClassNotFoundException, SQLException {
        Connection connection = getConnection();

        //query
        PreparedStatement preparedStatement = connection.prepareStatement("select id, name, password from userinfo where id = ?");
        preparedStatement.setLong(1, id);

        //실행
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        //결과매핑
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));

        //자원해지
        resultSet.close();
        preparedStatement.close();
        connection.close();
        //결과리턴
        return user;
    }




    public void insert(User user) throws SQLException, ClassNotFoundException {
        Connection connection = getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement("insert into userinfo (name, password) values (?,?)", Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1,user.getName());
        preparedStatement.setString(2,user.getPassword());
        preparedStatement.executeQuery();

        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        resultSet.next();

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }

    public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
//        //mysql
//        //driver loading
//        Class.forName("com.mysql.cj.jdbc.Driver");
//        //connection
//        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/jeju?");
//        return connection;
//    }
}
