package my.code.http.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/first")
public class FirstServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getHeader("user-agent");
        var headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            var name = headerNames.nextElement();
            var value = resp.getHeader(name);
            System.out.println(name + ": " + value);
        }

        resp.setContentType("text/html; charset=UTF-8");
        resp.setHeader("token", " 12345");
//        resp.setCharacterEncoding("UTF-8");
        try (var writer = resp.getWriter()) {
            writer.write("<h1>Hello from First Servlet!</h2>");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
