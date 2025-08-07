package my.code.http.redirecting;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/display-info")
public class DisplayServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Відображення Інформації</title></head><body>");
        out.println("<h1>Інформація з різних областей видимості:</h1>");

        // --- Отримання атрибутів ---

        // З Request Scope
        String requestMessage = (String) request.getAttribute("requestMessage");
        String userName = (String) request.getAttribute("userName");
        out.println("<p><b>З Request Scope:</b></p>");
        out.println("<ul><li>Повідомлення: " + (requestMessage != null ? requestMessage : "N/A") + "</li>");
        out.println("<li>Ім'я користувача: " + (userName != null ? userName : "N/A") + "</li></ul>");

        // З Session Scope
        HttpSession session = request.getSession(false); // Не створюємо нову сесію, якщо її немає
        if (session != null) {
            Integer sessionVisitCount = (Integer) session.getAttribute("sessionVisitCount");
            Date lastSessionAccess = (Date) session.getAttribute("lastSessionAccess");
            out.println("<p><b>З Session Scope:</b></p>");
            out.println("<ul><li>Кількість візитів у сесії: " + (sessionVisitCount != null ? sessionVisitCount : "N/A") + "</li>");
            out.println("<li>Останній доступ до сесії: " + (lastSessionAccess != null ? lastSessionAccess : "N/A") + "</li></ul>");
        } else {
            out.println("<p><b>Сесія не активна.</b></p>");
        }

        // З Application Scope
        ServletContext context = getServletContext();
        Date appStartupTime = (Date) context.getAttribute("appStartupTime");
        Integer globalRequestCount = (Integer) context.getAttribute("globalRequestCount");
        out.println("<p><b>З Application Scope:</b></p>");
        out.println("<ul><li>Час запуску додатку: " + (appStartupTime != null ? appStartupTime : "N/A") + "</li>");
        out.println("<li>Глобальна кількість запитів: " + (globalRequestCount != null ? globalRequestCount : "N/A") + "</li></ul>");


        // --- Включення JSP-сторінки (include) ---
        out.println("<h2>Включений контент з JSP:</h2>");
        RequestDispatcher jspDispatcher = request.getRequestDispatcher("/WEB-INF/views/display.jsp");
        jspDispatcher.include(request, response); // Включаємо вміст JSP

        out.println("</body></html>");
    }
}
