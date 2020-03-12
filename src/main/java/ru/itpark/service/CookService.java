package ru.itpark.service;

import ru.itpark.domain.Recipe;
import ru.itpark.util.JdbcTemplate;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
//TODOпоиск отдельно по названию, отдельно по ингредиентам

//добавление, редактирование и удаление- для страницы администратора


public class CookService {
    public final List<Recipe> items = new LinkedList<>();

    public CookService() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> getAll() throws SQLException {
        return JdbcTemplate.executeQuery("jdbc:sqlite:D:/JAVA_STEP_TWO/cookbookfinal\\db1",
                "SELECT id, name, ingredients, description from recipes", resultSet -> new Recipe(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("ingredients"),
                        resultSet.getString("description")
                ));
    }

    public List<Recipe> searchByName(String name) throws SQLException {
        List<Recipe> foundByName = getAll();
        return foundByName.stream()
                .filter(o -> o.getName().contains(name))
                .collect(Collectors.toList());
    }
}

