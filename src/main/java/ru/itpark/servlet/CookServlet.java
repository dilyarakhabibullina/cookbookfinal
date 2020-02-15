package ru.itpark.servlet;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.itpark.domain.Recipe;
import ru.itpark.service.CookService;
import ru.itpark.util.JdbcTemplate1;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class CookServlet extends HttpServlet {
    //   private JdbcTemplate jdbcTemblate;
    //private JdbcTemplate jdbcTemplate;
    CookService service = new CookService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI().substring(req.getContextPath().length());
//        if (url.equals("/")) {
//            resp.getWriter().write("THE BEST COOKBOOK");
//        }
        if (url.equals("/")) {
            final String q = req.getParameter("q");
            req.setAttribute("myrecipes", "Мои рецепты");
            req.setAttribute("findByName", q);
            try {
                req.setAttribute("items", service.getAll());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            req.getRequestDispatcher("/WEB-INF/search.jsp").forward(req, resp);
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
        if (url.equals("/search")) {
            req.setAttribute("myrecipes", "Вот что нашлось");
            final Collection<Recipe> foundByName;
            try {
                foundByName = service.searchByName(req.getParameter("q"));

                Set<Recipe> items = new HashSet<>();
                items.addAll(foundByName);
                req.setAttribute("items", items);
                req.setAttribute("findByName", req.getParameter("findByName"));
                req.getRequestDispatcher("/WEB-INF/search.jsp").forward(req, resp);
            } catch (SQLException e) {
                e.printStackTrace();

            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final String action = req.getParameter("action");
        if (action.equals("save")) {
            final String id = req.getParameter("id");
            final String name = req.getParameter("name");
            final String ingredients = req.getParameter("ingredients");
            final String description = req.getParameter("description");
            try {
                service.saveDataBase(new Recipe(id, name, ingredients, description));
            } catch (SQLException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }

           // service.save(id, district, undergrounds, price, file, uploadPath);
            // чтобы повторно не отрисовывать, отправляем его на страницу, где итак отрисовывается весь список
            resp.sendRedirect(req.getRequestURI());
            return;
            //resp.sendRedirect(req.getRequestURI());
//            req.setAttribute("items", service.saveDataBase(recipe));
//            req.getRequestDispatcher("/WEB-INF/search.jsp").forward(req, resp);

        }
        //
        //eq.setAttribute("myrecipes", "Мои рецепты");

    }
}
//
//}



