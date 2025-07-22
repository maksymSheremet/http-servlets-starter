package my.code.http.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

@WebServlet("/process-form")
public class FormProcessorServlet extends HttpServlet {
    private Map<String, String[]> allParams;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>GET Parameters</title></head><body>");
        out.println("<h1>GET parameters are received:</h1>");

        String name = request.getParameter("name");
        String age = request.getParameter("age");

        out.println("<p>Name: " + (name != null ? name : "Not specified") + "</p>");
        out.println("<p>Age: " + (age != null ? age : "Not specified") + "</p>");

        out.println("<h2>All parameters:</h2>");
        allParams = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : allParams.entrySet()) {
            out.println("<p>" + entry.getKey() + ": " + Arrays.toString(entry.getValue()) + "</p>");
        }

        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // !!! Дуже важливо для POST-запитів з не-ASCII символами
        request.setCharacterEncoding("UTF-8");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>POST Parameters</title></head><body>");
        out.println("<h1>POST parameters received:</h1>");

        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String[] interests = request.getParameterValues("interests"); // Для множинного вибору

        out.println("<p>User name: " + (userName != null ? userName : "Not specified") + "</p>");
        out.println("<p>Email: " + (email != null ? email : "Not specified") + "</p>");

        out.println("<p>Interests:");
        if (interests != null && interests.length > 0) {
            out.println("<ul>");
            for (String interest : interests) {
                out.println("<li>" + interest + "</li>");
            }
            out.println("</ul>");
        } else {
            out.println("Not specified.");
        }
        out.println("</p>");

        out.println("<h2>All parameters (from Map):</h2>");
        allParams = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : allParams.entrySet()) {
            out.println("<p>" + entry.getKey() + ": " + Arrays.toString(entry.getValue()) + "</p>");
        }

        out.println("</body></html>");
    }

}
