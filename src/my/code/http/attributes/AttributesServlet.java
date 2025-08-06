package my.code.http.attributes;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/attributes")
public class AttributesServlet extends HttpServlet {
    private static final String USER = "user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.getSession().getAttribute(USER);
//        req.getServletContext().getAttribute(USER);
        var session = req.getSession();
        var user = (UserDto) session.getAttribute(USER);
        if (user == null) {
            user = UserDto.builder()
                    .id(25L)
                    .mail("test@gmail.com")
                    .build();
            session.setAttribute(USER, user);
        }
    }
}
