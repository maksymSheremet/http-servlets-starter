package my.code.http.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/api/users")
public class UserApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        request.setCharacterEncoding("UTF-8"); // Важливо для JSON з UTF-8 символами
        response.setContentType("application/json;charset=UTF-8"); // Відповідь буде JSON

        PrintWriter out = response.getWriter();

        try {
            // 1. Читаємо все тіло запиту як рядок
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            String requestBody = sb.toString();

            if (requestBody.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 Bad Request
                out.print("{\"error\": \"Тіло запиту порожнє\"}");
                return;
            }

            // 2. Ручний парсинг JSON-рядка
            // Цей метод парсингу дуже простий і розрахований на конкретний формат JSON.
            // Він не є надійним для складних або довільних JSON-структур.
            String firstName = "N/A";
            String lastName = "N/A";
            int age = 0;

            // Шукаємо "firstName"
            int firstNameIndex = requestBody.indexOf("\"firstName\":");
            if (firstNameIndex != -1) {
                int startQuote = requestBody.indexOf("\"", firstNameIndex + "\"firstName\":".length());
                if (startQuote != -1) {
                    int endQuote = requestBody.indexOf("\"", startQuote + 1);
                    if (endQuote != -1) {
                        firstName = requestBody.substring(startQuote + 1, endQuote);
                    }
                }
            }

            // Шукаємо "lastName"
            int lastNameIndex = requestBody.indexOf("\"lastName\":");
            if (lastNameIndex != -1) {
                int startQuote = requestBody.indexOf("\"", lastNameIndex + "\"lastName\":".length());
                if (startQuote != -1) {
                    int endQuote = requestBody.indexOf("\"", startQuote + 1);
                    if (endQuote != -1) {
                        lastName = requestBody.substring(startQuote + 1, endQuote);
                    }
                }
            }

            // Шукаємо "age"
            int ageIndex = requestBody.indexOf("\"age\":");
            if (ageIndex != -1) {
                int startNum = ageIndex + "\"age\":".length();
                int endNum = requestBody.indexOf(",", startNum); // Шукаємо кому або закриваючу дужку
                if (endNum == -1) {
                    endNum = requestBody.indexOf("}", startNum);
                }
                if (endNum != -1) {
                    try {
                        String ageStr = requestBody.substring(startNum, endNum).trim();
                        age = Integer.parseInt(ageStr);
                    } catch (NumberFormatException e) {
                        getServletContext().log("Помилка парсингу віку: " + e.getMessage());
                    }
                }
            }

            // Логуємо отримані дані
            getServletContext().log("Отримано користувача (ручний парсинг): " + firstName + " " + lastName + ", " + age + " років.");

            // 3. Формуємо JSON-відповідь вручну
            out.print("{\"message\": \"Користувач успішно отриманий (без Jackson)\", ");
            out.print("\"receivedData\": {");
            out.print("\"firstName\": \"" + escapeJson(firstName) + "\", "); // Екрануємо символи для JSON
            out.print("\"lastName\": \"" + escapeJson(lastName) + "\", ");
            out.print("\"age\": " + age);
            out.print("}}");

            response.setStatus(HttpServletResponse.SC_OK); // 200 OK

        } catch (IOException e) {
            getServletContext().log("Помилка читання тіла запиту: ", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            out.print("{\"error\": \"Помилка сервера при читанні запиту\"}");
        } catch (Exception e) {
            getServletContext().log("Неочікувана помилка: ", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500 Internal Server Error
            out.print("{\"error\": \"Внутрішня помилка сервера\"}");
        }
    }

    /**
     * Допоміжний метод для екранування спеціальних символів у JSON-рядках.
     * Дуже спрощена версія, не обробляє всі можливі випадки.
     */
    private String escapeJson(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
