package ru.itpark.service;

import ru.itpark.domain.Recipe;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.itpark.util.JdbcTemplate1;

import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
//TODOпоиск отдельно по названию, отдельно по ингредиентам

//добавление, редактирование и удаление- для страницы администратора


public class CookService {
    public CookService() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // public final List<Recipe> items = new LinkedList<>();
    //private final JdbcTemplate jdbcTemplate;
    // private final JdbcTemplate1 jdbcTemplate1;


    // public Collection<Recipe> getInserted() {
//        return items;
//    }

//    public CookService(JdbcTemplate1 jdbcTemplate1) {
//        this.jdbcTemplate1 = jdbcTemplate1;
//        try {
//            Class.forName("org.sqlite.JDBC");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }

    //  public CookService() {


    public List<Recipe> getAll() throws SQLException {
        return JdbcTemplate1.executeQuery("jdbc:sqlite:D:/JAVA_STEP_TWO/cookbookfinal\\db1",
                "SELECT id, name, ingredients, description from recipes", resultSet -> new Recipe(
                        resultSet.getString("id"),
                        resultSet.getString("name"),
                        resultSet.getString("ingredients"),
                        resultSet.getString("description")
                ));
    }

    public Recipe saveDataBase(Recipe recipe) throws SQLException {
        return JdbcTemplate1.executeUpdate("jdbc:sqlite:D:/JAVA_STEP_TWO/cookbookfinal\\db1",
                "INSERT INTO recipes VALUES ('3','kasha', 'foo', 'nevkusno')");
//"INSERT INTO pilots (pilotname, birthdate, experience, aircraft)  VALUES (?,?,?,?)"
    }

    public List<Recipe> searchByName(String name) throws SQLException {
        List<Recipe> foundByName = getAll();
        return foundByName.stream()
                .filter(o -> o.getName().contains(name))
                .collect(Collectors.toList());
    }

//    public void save(String id, String name, String ingredients, String description) {
//        if (id.equals("")) {
//            id = generateId();
//            // writeFile(id, file, path);
//            insert(new Recipe(id, name, ingredients, description));
//            return;
//        }
//        update (new Recipe(id, name, ingredients, description));
//    }Re

//    public List<Recipe> save(Recipe item) throws SQLException {
//        // if (pilot.getId() == 0) {
//        return jdbcTemplate1.executeQuery("jdbc:sqlite:D:/JAVA_STEP_TWO/cookbookfinal\\db1", "INSERT INTO recipes (id, name, ingredients, description)  VALUES (?,?,?,?)",
//                resultSet -> new Recipe(
//                        resultSet.getString("id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("ingredients"),
//                        resultSet.getString("description")
//                ));
//
    //  } else {
    //  jdbcTemplate.update("UPDATE pilots SET pilotname = ?, birthdate = ?, experience = ?, aircraft = ? WHERE id = ?", pilot.getPilotname(), pilot.getBirthdate(), pilot.getExperience(), pilot.getAircraft(), pilot.getId()
    // );


    //}
//    private void update(Recipe recipe) {
//        boolean removed = items.removeIf(o -> o.getId().equals(recipe.getId()));
//        items.add(recipe);
//    }
//
//    private String generateId() {
//        return UUID.randomUUID().toString();
//    }
//
//    private void insert(Recipe recipe) {
//        items.add(recipe);
//    }
}

