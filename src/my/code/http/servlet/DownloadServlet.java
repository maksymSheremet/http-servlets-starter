package my.code.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Disposition", "attachment; filename=\"filename.txt\"");
//        resp.setContentType("text/plain");
        resp.setContentType("application/json");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

//        Files.readAllBytes(Path.of("resources", "first.json"));
//        try (var printWriter = resp.getWriter()) {
//            printWriter.write("Data from Servlet!");
//        }
        try (var outputStream = resp.getOutputStream();
             var stream = DownloadServlet.class.getClassLoader().getResourceAsStream("first.json")) {
            outputStream.write(stream.readAllBytes());
        }
    }
}
