package ru.itpark.servlet;

import ru.itpark.domain.Recipe;
import ru.itpark.service.CookService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class CookServlet extends HttpServlet {
    CookService service = new CookService ( );

    public CookServlet() throws SQLException {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding ("UTF-8");
        String url = req.getRequestURI ( ).substring (req.getContextPath ( ).length ( ));

        if (url.equals ("/")) {
            final String q = req.getParameter ("q");
            req.setAttribute ("myrecipes", "Мои рецепты");
            req.setAttribute ("findByName", q);
            try {
                req.setAttribute ("items", service.getAll ( ));
            } catch (SQLException e) {
                e.printStackTrace ( );
            }
            req.getRequestDispatcher ("/WEB-INF/search.jsp").forward (req, resp);
        }
//        if (url.equals("/search")) {
//            final String findByName = req.getParameter("findByName");
//            final Collection<Recipe> items;
//            try {
//                items = service.searchByName(findByName);
//                req.setAttribute("items", items);
//                req.setAttribute("findByName", findByName);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//            req.getRequestDispatcher("/search.jsp").forward(req, resp);
//            return;
//        }
        if (url.equals ("/search")) {

            req.setAttribute ("myrecipes", "Вот что нашлось");
            final Collection<Recipe> foundByName;
            try {
                foundByName = service.searchByName (req.getParameter ("q"));

                Set<Recipe> items = new HashSet<> ( );
                items.addAll (foundByName);
                req.setAttribute ("items", items);
                req.setAttribute ("findByName", req.getParameter ("findByName"));
                req.getRequestDispatcher ("/WEB-INF/search.jsp").forward (req, resp);
            } catch (SQLException e) {
                e.printStackTrace ( );
            }

            }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding ("UTF-8");
        final String action = req.getParameter ("action");
        Recipe recipe = new Recipe ( );
        if (action.equals ("save")) {
            //  final String id = req.getParameter("id");
            final String name = req.getParameter ("name");
            final String ingredients = req.getParameter ("ingredients");
            final String description = req.getParameter ("description");
            try {
                service.saveDataBase (new Recipe (recipe.generatedId ( ), name, ingredients, description));
            } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace ( );
            }
            resp.sendRedirect (req.getRequestURI ( ));
            return;
        }
        if (action.equals ("remove")) {
            final String id = req.getParameter ("id");
            // final String name = req.getParameter("name");
            //final String ingredients = req.getParameter("ingredients");
            // final String description = req.getParameter("description");
            try {
                service.removeById (id);
            } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace ( );
            }
            resp.sendRedirect (req.getRequestURI ( ));
            return;
        }

        if (action.equals ("edit")) {
            final String id = req.getParameter ("id");
            // final Recipe item = service.getById(id);

            try {
                try {
                    req.setAttribute ("item", service.getById (id));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace ( );
                } catch (NoSuchMethodException e) {
                    e.printStackTrace ( );
                } catch (IllegalAccessException e) {
                    e.printStackTrace ( );
                } catch (InvocationTargetException e) {
                    e.printStackTrace ( );
                } catch (InstantiationException e) {
                    e.printStackTrace ( );
                }

            } catch (SQLException e) {
                e.printStackTrace ( );
            }
/*
String i= service.getAll ();
String items = service.removeById(id);
*/
            try {
                               // req.setAttribute ("items", service.removeById (id));
                               req.setAttribute ("items", service.deleteId (id));
            } catch (SQLException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace ( );
            }

            req.getRequestDispatcher ("/WEB-INF/search.jsp").forward (req, resp);
            return;
        }
//
    }
}
//
//}



