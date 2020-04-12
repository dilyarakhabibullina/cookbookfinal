package ru.itpark.service;

import ru.itpark.domain.Recipe;
import ru.itpark.util.JdbcTemplate1;

import javax.sql.ConnectionPoolDataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

//TODOпоиск отдельно по названию, отдельно по ингредиентам

//добавление, редактирование и удаление- для страницы администратора


public class CookService {
    public CookService() throws SQLException {
        try {
            Class.forName ("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace ( );
        }
    }

    //public final List<Recipe> items = new LinkedList<>();
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
        return JdbcTemplate1.executeQuery ("jdbc:sqlite:D:/JAVA_STEP_TWO/cookbookfinal\\db1",
                "SELECT id, name, ingredients, description from recipes", resultSet -> new Recipe (
                        resultSet.getString ("id"),
                        resultSet.getString ("name"),
                        resultSet.getString ("ingredients"),
                        resultSet.getString ("description")
                ));
    }
//public String generatedId () {
//    return UUID.randomUUID().toString();
//}

    public int saveDataBase(Recipe recipe) throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String url = "jdbc:sqlite:D:/JAVA_STEP_TWO/cookbookfinal\\db1";
        //String id = recipe.getId();


        //String id = generatedId();


        Class.forName ("org.sqlite.JDBC").getDeclaredConstructor ( ).newInstance ( );
        Connection conn = DriverManager.getConnection (url);
        {
            String sql = "INSERT INTO recipes (id, name, ingredients, description) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = conn.prepareStatement (sql);
            {
                preparedStatement.setString (1, recipe.generatedId ( ));
                preparedStatement.setString (2, recipe.getName ( ));
                preparedStatement.setString (3, recipe.getIngredients ( ));
                preparedStatement.setString (4, recipe.getDescription ( ));

                return preparedStatement.executeUpdate ( );
            }

        }

//        return JdbcTemplate1.executeUpdate("jdbc:sqlite:D:/JAVA_STEP_TWO/cookbookfinal\\db1",
//                "INSERT INTO recipes VALUES ('5','kashka', 'grecha', 'tak sebe')");
////"INSERT INTO pilots (pilotname, birthdate, experience, aircraft)  VALUES (?,?,?,?)"
    }

    public List<Recipe> searchByName(String name) throws SQLException {
        List<Recipe> foundByName = getAll ( );
        return foundByName.stream ( )
                          .filter (o -> o.getName ( ).contains (name))
                          .collect (Collectors.toList ( ));
    }

    public int removeById(String id) throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String url = "jdbc:sqlite:D:/JAVA_STEP_TWO/cookbookfinal\\db1";
        Class.forName ("org.sqlite.JDBC").getDeclaredConstructor ( ).newInstance ( );
        Connection conn = DriverManager.getConnection (url);
        {
            String sql = "DELETE FROM recipes WHERE id=?";
            PreparedStatement preparedStatement = conn.prepareStatement (sql);
            {
                preparedStatement.setString (1, id);

                return preparedStatement.executeUpdate ( );
            }

        }
    }

    public int edit(String name, String ingredients, String description) throws SQLException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String url = "jdbc:sqlite:D:/JAVA_STEP_TWO/cookbookfinal\\db1";
        Class.forName ("org.sqlite.JDBC").getDeclaredConstructor ( ).newInstance ( );
        Connection conn = DriverManager.getConnection (url);
        {
            String sql = "UPDATE recipes SET name=?, ingredients=?, description=?; ";
            PreparedStatement preparedStatement = conn.prepareStatement (sql);
            {
                //preparedStatement.setString(1, id);
                preparedStatement.setString (1, name);
                preparedStatement.setString (2, ingredients);
                preparedStatement.setString (3, description);

                return preparedStatement.executeUpdate ( );
            }

        }
        //public int getById(String id) throws ClassNotFoundException, NoSuchMethodException, SQLException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        String url = "jdbc:sqlite:D:/JAVA_STEP_TWO/cookbookfinal\\db1";
//        Class.forName ("org.sqlite.JDBC").getDeclaredConstructor ( ).newInstance ( );
//        Connection conn = DriverManager.getConnection (url);
//        {
//            String sql = "SELECT * FROM recipes WHERE id=?";
//            PreparedStatement preparedStatement = conn.prepareStatement (sql);
//            {
//                preparedStatement.setString (1, id);
//
//                return preparedStatement.executeUpdate ( );
//            }
//
    }

    public Recipe getById(String id) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, SQLException {
        Recipe recipe = null;
        Class.forName ("org.sqlite.JDBC").getDeclaredConstructor ( ).newInstance ( );
        Connection conn = DriverManager.getConnection ("jdbc:sqlite:D:/JAVA_STEP_TWO/cookbookfinal\\db1");
        PreparedStatement pr = conn.prepareStatement ("SELECT id, name, ingredients, description from recipes WHERE id=?");
        pr.setString (1, id);
        ResultSet rs = pr.executeQuery ( );
        if (rs.next ( )) {
            recipe = new Recipe ( );
            recipe.setId (rs.getString (1));
            recipe.setName (rs.getString (2));
            recipe.setIngredients (rs.getString (3));
            recipe.setDescription (rs.getString (4));
            conn.close ( );
        }
        return recipe;
    }
    public List<Recipe> deleteId(String id) throws SQLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Recipe recipe = getById (id);
        id = recipe.getId ();
        removeById (id);
        List<Recipe> foundToEdit = getAll();
        return foundToEdit;


       //int nalfi = removeById (id);

        //String = getById (String id);
//        return foundToEdit.stream ( )
//                          .filter (o -> o.getId ( )!=id)
//                          .collect (Collectors.toList ( ));
    }

//        return JdbcTemplate1.executeQuery (,
//                , resultSet -> new Recipe (
//                        resultSet.getString ("id"),
//                        resultSet.getString ("name"),
//                        resultSet.getString ("ingredients"),
//                        resultSet.getString ("description")
//                ));

//    public Recipe getById(String id) throws ClassNotFoundException, NoSuchMethodException, SQLException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        String url = "jdbc:sqlite:D:/JAVA_STEP_TWO/cookbookfinal\\db1";
//        Class.forName ("org.sqlite.JDBC").getDeclaredConstructor ( ).newInstance ( );
//        Connection conn = DriverManager.getConnection (url);
//        {
//            String sql = "SELECT *  FROM recipes WHERE id=?";
//            PreparedStatement preparedStatement = conn.prepareStatement (sql);
//            {
//                preparedStatement.setString (1, id);
//               // preparedStatement.setString (2, name);
//                //preparedStatement.setString (3, ingredients);
//                //preparedStatement.setString (4, description);
//
//                return (Recipe) preparedStatement.executeQuery ( );
//            }
////
////        }
//        }
//    }
}
//    public int getById(String id) throws ClassNotFoundException, NoSuchMethodException, SQLException, IllegalAccessException, InvocationTargetException, InstantiationException {
//        String url = "jdbc:sqlite:D:/JAVA_STEP_TWO/cookbookfinal\\db1";
//        Class.forName ("org.sqlite.JDBC").getDeclaredConstructor ( ).newInstance ( );
//        Connection conn = DriverManager.getConnection (url);
//        {
//            String sql = "SELECT * FROM recipes WHERE id=?";
//            PreparedStatement preparedStatement = conn.prepareStatement (sql);
//            {
//                preparedStatement.setString (1, id);
//
//                return preparedStatement.executeUpdate ( );
//            }
//
//        }
//    }
//    public List<Recipe> getById() throws SQLException {
//
//        return JdbcTemplate1.executeQuery ("jdbc:sqlite:D:/JAVA_STEP_TWO/cookbookfinal\\db1",
//                "SELECT id, name, ingredients, description from recipes WHERE id=?", resultSet -> new Recipe (
//                        resultSet.getString ("id"),
//                        resultSet.getString ("name"),
//                        resultSet.getString ("ingredients"),
//                        resultSet.getString ("description")
//                ));
//    }


//private final Collection<Recipe> items = new LinkedList<> (getAll());

//
//    public Collection<Recipe> getItAll() {
//        return items;
//    }

//    public Recipe getById(String id) {
//       //Collection<Recipe> items = new LinkedList<>();
//        Recipe item = items.stream ()
//                .filter(o -> o.getId().equals(id))
//                           .findAny ( );
//        return item;
//                }

//    public Recipe getById(String id) {
//        //List<Recipe> resultDistrict = new ArrayList<> ();
//        Recipe item = new Recipe ( );
//        for (Recipe item1 : items) {
//            if (item1.getId ( ).equals (id)) {
//                //resultDistrict.add(id);
//            }
//        }
//        return item;
////
//   public House getById(String id) {
//        return items.stream()
//                .filter(o -> o.getId().equals(id))
//                .findAny()
//                .orElseThrow(NotFoundException::new);
//    }

