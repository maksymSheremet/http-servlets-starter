package my.code.http.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        urlPatterns = "/config-example",
        initParams = {
                @WebInitParam(name = "greeting", value = "Greetings from the serviette!"),
                @WebInitParam(name = "author", value = "Maksym"),
                @WebInitParam(name = "version", value = "1.0")
        },
        loadOnStartup = 1 // Ініціалізувати при запуску додатку
)
public class ConfigExampleServlet extends HttpServlet {
    private String servletGreeting;
    private String servletAuthor;
    private ServletContext applicationContext; // Зберігаємо посилання на ServletContext

    @Override
    // Метод init() приймає об'єкт ServletConfig
    public void init(ServletConfig config) throws ServletException {
        // Завжди викликайте super.init(config), якщо ви не перевизначаєте його повністю
        // або використовуйте init() без параметрів, якщо вам не потрібен ServletConfig
        super.init(config); // Або просто super.init(); якщо не використовуємо config напряму тут

        String charset = config.getInitParameter("charset");
        getServletContext().setAttribute("charset", charset);

        // Отримання параметрів ініціалізації, специфічних для цього сервлету
        this.servletGreeting = config.getInitParameter("greeting");
        this.servletAuthor = config.getInitParameter("author");
        String version = config.getInitParameter("version"); // Можемо використовувати локально

        // Отримання посилання на ServletContext
        this.applicationContext = config.getServletContext();
        String appName = applicationContext.getInitParameter("appName"); // Отримуємо параметр з web.xml або @WebInitParam для ServletContext

        System.out.println("----------------------------------------");
        System.out.println("Servlet '" + config.getServletName() + "' initialized.");
        System.out.println("Message: " + this.servletGreeting);
        System.out.println("Author: " + this.servletAuthor);
        System.out.println("Servlet version: " + version);
        System.out.println("Application name (from ServletContext): " + appName); // Може бути null, якщо не задано
        System.out.println("----------------------------------------");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Example ServletConfig</title></head><body>");
        out.println("<h1>" + this.servletGreeting + "</h1>");
        out.println("<p>This servlet wrote: " + this.servletAuthor + "</p>");
        out.println("<p>The name of this servlet is: " + getServletConfig().getServletName() + "</p>");
        out.println("<p>Application name (from the saved ServletContext): " + applicationContext.getInitParameter("appName") + "</p>");

        out.println("<h2>All parameters for initializing this servlet:</h2>");
        out.println("<ul>");
        java.util.Enumeration<String> paramNames = getServletConfig().getInitParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            out.println("<li>" + paramName + ": " + getServletConfig().getInitParameter(paramName) + "</li>");
        }
        out.println("</ul>");

        out.println("</body></html>");
    }

    @Override
    public void destroy() {
        System.out.println("Servlet '" + getServletConfig().getServletName() + "' destroyed.");
    }
}
