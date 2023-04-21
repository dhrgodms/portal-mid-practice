package kr.ac.jejunu.user;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcContext {
    private final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void jdbcContextForUpdate(StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.makeStatement(connection);
            preparedStatement.executeUpdate();

        } finally {
            try {
                preparedStatement.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
            try {
                connection.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void jdbcContextForInsert(User user, StatementStrategy statementStrategy) throws SQLException {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = statementStrategy.makeStatement(connection);
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getLong(1));
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public User jdbcContextForFindById(StatementStrategy statementStrategy) throws SQLException {
        Connection connection=null;
        ResultSet resultSet = null;
        User user = null;
        PreparedStatement preparedStatement=null;
        // 여기에서 예외처리 해줌~
        try {
            connection = dataSource.getConnection();

            //query
            preparedStatement = statementStrategy.makeStatement(connection);

            //실행
            resultSet = preparedStatement.executeQuery();
            //결과매핑
            if(resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
            }
        } finally {
            //자원해지
            //이거 exception 안뜰수도 있으니까 대충 외워 별거없음(이라는 마음으로)
            try{
                resultSet.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
            try {
                connection.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        //결과리턴
        return user;
    }
}
