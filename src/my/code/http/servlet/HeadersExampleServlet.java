package my.code.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet("/headers-example")
public class HeadersExampleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // --- Отримання заголовків запиту ---
        String userAgent = req.getHeader("user-agent");
        String acceptLanguage = req.getHeader("accept-language");
        long ifModifiedSince = req.getDateHeader("If-Modified-Since"); // Дата останньої модифікації в кеші клієнта

        System.out.println("Request from User-Agent: " + userAgent);
        System.out.println("Preferred language: " + acceptLanguage);
        if (ifModifiedSince != -1) {
            System.out.println("The client requests data if it has been changed after: " + new Date(ifModifiedSince));
        }

        // --- Встановлення заголовків відповіді ---
        resp.setContentType("text/html;charset=UTF-8"); // Встановлюємо Content-Type
        resp.setHeader("Server", "MyCustomServletServer/1.0"); // Додаємо кастомний заголовок
        resp.addHeader("X-Powered-By", "Java Servlets"); // Додаємо ще один
        resp.setDateHeader("Date", System.currentTimeMillis()); // Встановлюємо поточну дату

        // Керування кешуванням: дозволяємо кешування на 60 секунд
        resp.setHeader("Cache-Control", "public, max-age=60");
        resp.setDateHeader("Expires", System.currentTimeMillis() + 60 * 1000); // 60 секунд у майбутньому

        // Приклад умовної відповіді на основі заголовка If-Modified-Since
        // У реальному додатку тут була б логіка перевірки, чи змінилися дані
        if (ifModifiedSince != -1 && new Date(ifModifiedSince).after(new Date(System.currentTimeMillis() - 300000))) { // Якщо дані змінені менше 5 хвилин тому
            resp.setStatus(HttpServletResponse.SC_NOT_MODIFIED); // 304 Not Modified
            System.out.println("Data not changed, sent 304 Not Modified.");
            return; // Важливо! Припинити виконання, щоб не відправляти тіло
        }


        // --- Надсилання тіла відповіді ---
        PrintWriter out = resp.getWriter();
        out.println("<html><head><title>Example of Headings</title></head><body>");
        out.println("<h1>Information about HTTP headers</h1>");
        out.println("<p>Your User-Agent: " + userAgent + "</p>");
        out.println("<p>Preferred language: " + acceptLanguage + "</p>");
        out.println("<p>The content will be cached for 60 seconds.</p>");
        out.println("</body></html>");
    }
}
