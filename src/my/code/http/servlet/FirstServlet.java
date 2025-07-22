package my.code.http.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

@WebServlet("/first")
public class FirstServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var paramValue = req.getParameter("param");
        var parameterMap = req.getParameterMap();
        System.out.println(paramValue);
        parameterMap.forEach((key, values) -> {
            System.out.println(key + ": " + Arrays.toString(values));
        });

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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var parameterMap = req.getParameterMap();
        parameterMap.forEach((key, values) -> {
            System.out.println(key + ": " + Arrays.toString(values));
        });
        try (var reader = req.getReader();
             var lines = reader.lines()) {
            lines.forEach(System.out::println);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
