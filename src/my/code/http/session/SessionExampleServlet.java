package my.code.http.session;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet("/session-example")
public class SessionExampleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();

        // Отримуємо об'єкт сесії. Якщо її немає, вона буде створена.
        HttpSession session = req.getSession();

        out.println("<html><head><title>Приклад Session</title></head><body>");
        out.println("<h1>Робота з HTTP Session</h1>");

        // --- Інформація про сесію ---
        out.println("<p>ID сесії: <b>" + session.getId() + "</b></p>");
        out.println("<p>Час створення сесії: " + new Date(session.getCreationTime()) + "</p>");
        out.println("<p>Час останнього доступу: " + new Date(session.getLastAccessedTime()) + "</p>");
        out.println("<p>Тайм-аут неактивності (секунд): " + session.getMaxInactiveInterval() + "</p>");
        out.println("<p>Чи нова сесія? " + session.isNew() + "</p>");

        // --- Робота з атрибутами сесії ---
        String userName = (String) session.getAttribute("userName");
        Integer visitCount = (Integer) session.getAttribute("visitCount");
        List<String> cartItems = (List<String>) session.getAttribute("cartItems");

        if (userName == null) {
            userName = "Гість";
            session.setAttribute("userName", "Користувач_" + session.getId().substring(0, 5)); // Встановлюємо ім'я для нової сесії
            out.println("<p><b>Привіт, нова сесія!</b> Ваше ім'я встановлено як: " + session.getAttribute("userName") + "</p>");
        } else {
            out.println("<p>Привіт, <b>" + userName + "</b>! (з сесії)</p>");
        }

        if (visitCount == null) {
            visitCount = 1;
            out.println("<p>Це ваш перший візит у цій сесії.</p>");
        } else {
            visitCount++;
            out.println("<p>Ви відвідали цю сторінку <b>" + visitCount + "</b> разів у цій сесії.</p>");
        }
        session.setAttribute("visitCount", visitCount);

        if (cartItems == null) {
            cartItems = new ArrayList<>();
            out.println("<p>Ваш кошик порожній.</p>");
        } else {
            out.println("<h2>Елементи в кошику:</h2><ul>");
            if (cartItems.isEmpty()) {
                out.println("<li>Кошик порожній.</li>");
            } else {
                for (String item : cartItems) {
                    out.println("<li>" + item + "</li>");
                }
            }
            out.println("</ul>");
        }
        session.setAttribute("cartItems", cartItems); // Оновлюємо кошик у сесії

        // --- Додавання елементу в кошик ---
        out.println("<h2>Додати елемент до кошика:</h2>");
        out.println("<form action=\"session-example\" method=\"POST\">");
        out.println("<input type=\"text\" name=\"itemToAdd\" placeholder=\"Назва товару\">");
        out.println("<input type=\"submit\" value=\"Додати до кошика\">");
        out.println("</form>");

        // --- Видалення сесії ---
        out.println("<h2>Видалити сесію:</h2>");
        out.println("<form action=\"session-example\" method=\"POST\">");
        out.println("<input type=\"hidden\" name=\"action\" value=\"invalidateSession\">");
        out.println("<input type=\"submit\" value=\"Вийти (Знищити сесію)\">");
        out.println("</form>");

        out.println("<br><a href=\"session-example\">Оновити сторінку, щоб побачити зміни</a>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(); // Отримуємо поточну сесію

        String action = req.getParameter("action");

        if ("invalidateSession".equals(action)) {
            session.invalidate(); // Знищуємо сесію
            resp.setContentType("text/html;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            out.println("<html><head><title>Сесія Знищена</title></head><body>");
            out.println("<h1>Ваша сесія була знищена.</h1>");
            out.println("<p><a href=\"session-example\">Почати нову сесію</a></p>");
            out.println("</body></html>");
        } else {
            // Додавання елементу до кошика
            String itemToAdd = req.getParameter("itemToAdd");
            if (itemToAdd != null && !itemToAdd.trim().isEmpty()) {
                List<String> cartItems = (List<String>) session.getAttribute("cartItems");
                if (cartItems == null) {
                    cartItems = new ArrayList<>();
                }
                cartItems.add(itemToAdd.trim());
                session.setAttribute("cartItems", cartItems);
            }
            // Після додавання/зміни, перенаправляємо на GET-метод, щоб оновити сторінку
            resp.sendRedirect(req.getContextPath() + "/session-example");
        }
    }
}
