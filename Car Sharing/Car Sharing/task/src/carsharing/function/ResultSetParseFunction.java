package carsharing.function;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetParseFunction<R> {
    R parse(ResultSet resultSet) throws SQLException;
}
