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
import java.util.Date;

@WebServlet("/process-data")
public class DataProcessorServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        // Встановлюємо атрибут в Application Scope при запуску додатка
        ServletContext context = getServletContext();
        context.setAttribute("appStartupTime", new Date());
        context.log("Атрибут 'appStartupTime' встановлено в ServletContext.");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // --- Встановлення атрибутів ---

        // 1. Атрибут в Request Scope
        request.setAttribute("requestMessage", "Це повідомлення для поточного запиту.");
        request.setAttribute("userName", "Олег"); // Передаємо ім'я користувача

        // 2. Атрибут в Session Scope
        HttpSession session = request.getSession();
        Integer sessionVisitCount = (Integer) session.getAttribute("sessionVisitCount");
        if (sessionVisitCount == null) {
            sessionVisitCount = 1;
        } else {
            sessionVisitCount++;
        }
        session.setAttribute("sessionVisitCount", sessionVisitCount);
        session.setAttribute("lastSessionAccess", new Date());

        // 3. Атрибут в Application Scope (оновлюємо глобальний лічильник)
        ServletContext context = getServletContext();
        Integer globalRequestCount = (Integer) context.getAttribute("globalRequestCount");
        if (globalRequestCount == null) {
            globalRequestCount = 1;
        } else {
            globalRequestCount++;
        }
        context.setAttribute("globalRequestCount", globalRequestCount);


        // --- Перенаправлення запиту (forward) ---
        // Передаємо керування обробкою запиту до DisplayServlet
        // Всі атрибути request, session, application будуть доступні в DisplayServlet
        RequestDispatcher dispatcher = request.getRequestDispatcher("/display-info");
        dispatcher.forward(request, response);

        // Після forward НЕ можна писати в response!
        // response.getWriter().println("Цей текст не буде відображено.");
    }
}
