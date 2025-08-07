package my.code.http.redirecting;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/dispatcher")
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getRequestDispatcher("/flights")
//                        .include(req, resp);

        req.getRequestDispatcher("/flights")
                .forward(req, resp);

//        resp.sendRedirect("/flights");

//        var requestDispatcher = req.getRequestDispatcher("/flights");
//        req.setAttribute("1", "243");
//        requestDispatcher.forward(req,resp);

//        getServletContext().getRequestDispatcher("/flights")
    }
}
