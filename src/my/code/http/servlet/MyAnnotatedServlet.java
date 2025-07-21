package my.code.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

// Приклад використання @WebServlet
@WebServlet(name = "MyAnnotatedServlet",
        urlPatterns = {"/my-app/home", "/my-app/index"},
        loadOnStartup = 1, // Ініціалізувати при запуску додатку
        initParams = {
                @WebInitParam(name = "welcomeMessage", value = "Welcome!"),
                @WebInitParam(name = "appVersion", value = "2.0")
        })
public class MyAnnotatedServlet extends HttpServlet {

    private String welcomeMessage;
    private String appVersion;

    // Метод init() викликається один раз при ініціалізації сервлету
    @Override
    public void init() throws ServletException {
        welcomeMessage = getServletConfig().getInitParameter("welcomeMessage");
        appVersion = getServletConfig().getInitParameter("appVersion");
        System.out.println("MyAnnotatedServlet initialisation. " + welcomeMessage + " Version: " + appVersion);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        out.println("<html><head><title>Example @WebServlet</title></head><body>");
        out.println("<h1>" + welcomeMessage + "</h1>");
        out.println("<p>This servlet is available via URLs: /my-app/-home or /my-app/index</p>");
        out.println("<p>Version of the application: " + appVersion + "</p>");
        out.println("</body></html>");
    }

    @Override
    public void destroy() {
        System.out.println("MyAnnotatedServlet destroy");
    }
}
