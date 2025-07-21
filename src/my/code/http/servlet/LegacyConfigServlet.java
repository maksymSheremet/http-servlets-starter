package my.code.http.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class LegacyConfigServlet extends HttpServlet {

    private String welcomeMessage;
    private String adminEmail;
    private ServletContext servletContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.welcomeMessage = config.getInitParameter("message");
        this.adminEmail = config.getInitParameter("adminEmail");
        this.servletContext = config.getServletContext(); // Отримання ServletContext

        System.out.println("----------------------------------------");
        System.out.println("LegacyConfigServlet initialized.");
        System.out.println("Message: " + this.welcomeMessage);
        System.out.println("Email of the administrator: " + this.adminEmail);
        System.out.println("Application name (from web.xml): " + this.servletContext.getInitParameter("appName"));
        System.out.println("----------------------------------------");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + this.welcomeMessage + "</h1>");
        out.println("<p>Please contact: " + this.adminEmail + "</p>");
        out.println("<p>Application name (from ServletContext): " + this.servletContext.getInitParameter("appName") + "</p>");
        out.println("</body></html>");
    }
}
