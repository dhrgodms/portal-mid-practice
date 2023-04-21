package kr.ac.jejunu.user;

import javax.sql.DataSource;
import java.sql.*;

public class UserDao {
    private final DataSource dataSource;

    public UserDao(DataSource dataSource){
        this.dataSource = dataSource;
    }

    public User findById(Long id) throws ClassNotFoundException, SQLException {

        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet = null;
        User user = null;
        // 여기에서 예외처리 해줌~
        try {
            connection = dataSource.getConnection();

            //query
            preparedStatement = connection.prepareStatement("select id, name, password from userinfo where id = ?");
            preparedStatement.setLong(1, id);

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




    public void insert(User user) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = connection.prepareStatement("insert into userinfo (name, password) values (?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.executeUpdate();

            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            user.setId(resultSet.getLong(1));
        } finally {
            try {
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
    }

    public void update(User user) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = connection.prepareStatement("update userinfo set name = ?, password = ? where id = ?");
            preparedStatement.setString(1,user.getName());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setLong(3, user.getId());

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

    public void delete(Long id) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = connection.prepareStatement("delete from userinfo where id = ?");
            preparedStatement.setLong(1, id);

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
}
