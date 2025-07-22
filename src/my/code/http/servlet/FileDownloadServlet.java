package my.code.http.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@WebServlet("/download_field")
public class FileDownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Отримання імені файлу з параметра запиту
        String fileName = request.getParameter("name"); // Приклад: /download?name=my_report.pdf

        if (fileName == null || fileName.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The file name is not specified.");
            return;
        }

        // 2. Визначення реального шляху до файлу на сервері
        // Припускаємо, що файли для завантаження знаходяться в директорії /WEB-INF/downloads/
        ServletContext context = getServletContext();
        String absolutePath = context.getRealPath("/WEB-INF/downloads/" + fileName);
        File downloadFile = new File(absolutePath);

        // Перевірка існування файлу та прав доступу (хоча getRealPath зазвичай не дозволяє вийти за межі контексту)
        if (!downloadFile.exists() || !downloadFile.isFile() || !downloadFile.canRead()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "The file '" + fileName + "' was not found or access is denied");
            context.log("Attempting to upload a non-existent/inaccessible file: " + absolutePath);
            return;
        }

        // 3. Встановлення HTTP-заголовків відповіді
        // Визначення MIME-типу файлу
        String mimeType = context.getMimeType(absolutePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream"; // Тип за замовчуванням для невідомих типів
        }
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());

        // Встановлення заголовка Content-Disposition для завантаження файлу
        // Кодування імені файлу для коректного відображення кирилиці та спецсимволів
        String encodedFileName = URLEncoder.encode(downloadFile.getName(), "UTF-8").replace("+", "%20");
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"; filename*=UTF-8''%s",
                encodedFileName, encodedFileName);
        // Заголовок filename*=UTF-8''filename (RFC 5987) краще підтримує кирилицю
        response.setHeader(headerKey, headerValue);

        // Встановлення заголовків для заборони кешування (опціонально, залежить від файлу)
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);


        // 4. Зчитування файлу та запис у вихідний потік відповіді
        try (FileInputStream inStream = new FileInputStream(downloadFile);
             OutputStream outStream = response.getOutputStream()) {

            byte[] buffer = new byte[4096]; // Буфер для читання блоками
            int bytesRead;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        } catch (IOException ex) {
            // Логування помилки
            context.log("Error reading or writing a file '" + fileName + "': " + ex.getMessage(), ex);
            // Відправлення помилки клієнту, якщо потік ще не закритий
            if (!response.isCommitted()) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while uploading a file.");
            }
        }
    }
}
