package kr.ac.jejunu.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FindByIdStatementStrategy implements StatementStrategy {
    private final Long id;

    public FindByIdStatementStrategy(Long id){
        this.id = id;
    }
    @Override
    public PreparedStatement makeStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select id, name, password from userinfo where id = ?");
        preparedStatement.setLong(1, id);
        return preparedStatement;
    }
}
