package my.code.http.cookie;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/cookie-example")
public class CookieExampleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        out.println("<html><head><title>Приклад Cookies</title></head><body>");
        out.println("<h1>Робота з Cookies</h1>");

        // --- 1. Читання кук ---
        Cookie[] cookies = req.getCookies();
        String lastVisitTime = "Немає інформації про останній візит.";
        String userName = "Гість";

        if (cookies != null) {
            out.println("<h2>Отримані Cookies:</h2><ul>");
            for (Cookie cookie : cookies) {
                out.println("<li><b>" + cookie.getName() + "</b>: " + cookie.getValue() +
                            " (Max-Age: " + cookie.getMaxAge() +
                            ", Path: " + cookie.getPath() + ")</li>");
                if ("lastVisit".equals(cookie.getName())) {
                    lastVisitTime = "Останній візит: " + cookie.getValue();
                }
                if ("userName".equals(cookie.getName())) {
                    userName = cookie.getValue();
                }
            }
            out.println("</ul>");
        } else {
            out.println("<p>Cookies не знайдено.</p>");
        }

        out.println("<p>Привіт, <b>" + userName + "</b>!</p>");
        out.println("<p>" + lastVisitTime + "</p>");

        // --- 2. Створення/Оновлення кук ---
        // Створення або оновлення куки "lastVisit"
        Cookie lastVisitCookie = new Cookie("lastVisit", String.valueOf(System.currentTimeMillis()));
        lastVisitCookie.setMaxAge(60 * 60 * 24 * 30); // 30 днів
        lastVisitCookie.setPath("/"); // Для всього додатку
        lastVisitCookie.setHttpOnly(true); // Захист від XSS
        resp.addCookie(lastVisitCookie);
        out.println("<p>Кука 'lastVisit' встановлена/оновлена.</p>");

        // Якщо користувач невідомий, встановлюємо куку "userName"
        if ("Гість".equals(userName)) {
            Cookie userCookie = new Cookie("userName", "НовийКористувач" + System.currentTimeMillis() % 1000);
            userCookie.setMaxAge(60 * 60 * 24 * 365); // 1 рік
            userCookie.setPath("/");
            resp.addCookie(userCookie);
            out.println("<p>Кука 'userName' встановлена.</p>");
        }

        // --- 3. Форма для видалення куки ---
        out.println("<h2>Видалити Cookie 'userName':</h2>");
        out.println("<form action=\"cookie-example\" method=\"POST\">");
        out.println("<input type=\"hidden\" name=\"action\" value=\"deleteUserCookie\">");
        out.println("<input type=\"submit\" value=\"Видалити Cookie 'userName'\">");
        out.println("</form>");

        out.println("<br><a href=\"cookie-example\">Оновити сторінку, щоб побачити зміни</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        if ("deleteUserCookie".equals(action)) {
            // Видалення куки "userName"
            Cookie userCookieToDelete = new Cookie("userName", "");
            userCookieToDelete.setMaxAge(0); // Встановлюємо термін життя 0
            userCookieToDelete.setPath("/"); // Важливо: шлях має співпадати
            resp.addCookie(userCookieToDelete);

            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<html><head><title>Cookie Видалено</title></head><body>");
            out.println("<h1>Cookie 'userName' успішно видалено!</h1>");
            out.println("<p><a href=\"cookie-example\">Повернутися до прикладу Cookies</a></p>");
            out.println("</body></html>");
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Невідома дія.");
        }
    }
}
