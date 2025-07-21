package my.code.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello")
public class MyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // --- HttpServletRequest (читаємо вхідні дані) ---
        String userName = req.getParameter("name"); // Отримуємо параметр з URL (наприклад, /hello?name=John)
        String userAgent = req.getHeader("User-Agent"); // Отримуємо заголовок User-Agent

        // --- HttpServletResponse (формуємо відповідь) ---
        resp.setContentType("text/html;charset=utf-8"); // Встановлюємо тип контенту та кодування
        PrintWriter out = resp.getWriter(); // Отримуємо потік для запису відповіді

        out.println("<html>");
        out.println("<head><title>Hello, Servlet</title></head>");
        out.println("<body>");
        if (userName != null && !userName.isEmpty()) {
            out.println("<h1>Hello," + userName + "!</h1>");
        } else {
            out.println("<h1>Hello, world!</h1>");
        }
        out.println("<p>Your browser: " + userAgent + "</p>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Приклад обробки POST-запиту
        String email = req.getParameter("email");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println("Thank you for your email: " + email);
    }
}
