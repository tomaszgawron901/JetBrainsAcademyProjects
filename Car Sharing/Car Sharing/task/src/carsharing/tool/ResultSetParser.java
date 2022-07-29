package carsharing.tool;

import carsharing.function.ResultSetParseFunction;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class ResultSetParser {
    public static <R> Optional<R> parse(ResultSet resultSet, ResultSetParseFunction<R> parser) throws SQLException {
        if (resultSet == null) {
            return Optional.empty();
        }

        if (parser == null) {
            throw new IllegalArgumentException("Parser is null");
        }

        if(!resultSet.next()) {
            return Optional.empty();
        }
        return Optional.ofNullable(parser.parse(resultSet));
    }

    public static <R> Optional<ArrayList<R>> parseList(ResultSet resultSet, ResultSetParseFunction<R> parser) throws SQLException {
        if (resultSet == null) {
            return Optional.empty();
        }

        if (parser == null) {
            throw new IllegalArgumentException("Parser is null");
        }

        ArrayList<R> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(parser.parse(resultSet));
        }
        return Optional.of(list);
    }
}
