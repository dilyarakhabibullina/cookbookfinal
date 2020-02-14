package ru.itpark.util;

import ru.itpark.domain.Recipe;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JdbcTemplate1 {
    public static Recipe executeUpdate(String url, String sql) throws SQLException {
       Recipe recipe = new Recipe();
        //  try {
        Connection connection = DriverManager.getConnection(url);
        Statement statement = connection.createStatement();
        int s = statement.executeUpdate(sql);
        //List<Recipe> result = new LinkedList<>();
        return recipe;
        //        }
        // catch()
        // {
//            List<T> result = new LinkedList<>();
//            while (resultSet.next()) {
//                result.add(mapper.map(resultSet));
//
//            }
        //    return null;
        //}
    }

    private JdbcTemplate1() {
    }

    public static <T> List<T> executeQuery(String url, String sql, RowMapper<T> mapper) throws SQLException {
        try (
                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql)) {
            List<T> result = new LinkedList<>();
            while (resultSet.next()) {
                result.add(mapper.map(resultSet));

            }

            return result;

        }
    }

//    public void update(String s, String id, String name, String ingredients, String description) {
//    }
}