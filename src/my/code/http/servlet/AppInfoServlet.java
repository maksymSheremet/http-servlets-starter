package my.code.http.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

// Можна визначити глобальні параметри у web.xml
// <context-param>
//    <param-name>appName</param-name>
//    <param-value>My Awesome App</param-value>
// </context-param>

@WebServlet("/app-info")
public class AppInfoServlet extends HttpServlet {

    private ServletContext applicationContext;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.applicationContext = config.getServletContext(); // Отримання через ServletConfig
        // або this.applicationContext = getServletContext(); // Отримання через успадкований метод

        String appName = applicationContext.getInitParameter("appName");
        if (appName == null) {
            appName = "Name of the application Not specified";
        }
        applicationContext.log("Initialization of the AppInfoServlet. The name of the application: " + appName);
        applicationContext.setAttribute("counter", 0); // Зберігаємо лічильник у ServletContext
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String appName = applicationContext.getInitParameter("appName");
        if (appName == null) {
            appName = "Name of the application Not specified";
        }

        // Збільшуємо глобальний лічильник
        Integer counter = (Integer) applicationContext.getAttribute("counter");
        if (counter == null) { // Мало б не трапитись через init(), але для безпеки
            counter = 0;
        }
        counter++;
        applicationContext.setAttribute("counter", counter);

        out.println("<html><head><title>Information about the Application</title></head><body>");
        out.println("<h1>" + appName + "</h1>");
        out.println("<p>Contextual path of the application: " + request.getContextPath() + "</p>");
        out.println("<p>The real root path of the application: " + applicationContext.getRealPath("/") + "</p>");
        out.println("<p>Servlet version API: " + applicationContext.getMajorVersion() + "." + applicationContext.getMinorVersion() + "</p>");
        out.println("<p>Number of visits to this servlet (global): " + counter + "</p>");

        // Приклад доступу до ресурсу
        try (InputStream is = applicationContext.getResourceAsStream("/META-INF/MANIFEST.MF")) {
            if (is != null) {
                out.println("<p>MANIFEST.MF found and available.</p>");
            } else {
                out.println("<p>MANIFEST.MF not found.</p>");
            }
        } catch (Exception e) {
            applicationContext.log("Reading error MANIFEST.MF", e);
            out.println("<p>Reading error MANIFEST.MF: " + e.getMessage() + "</p>");
        }

        applicationContext.log("The AppInfoServlet has been visited. Current counter: " + counter);

        out.println("</body></html>");
    }
}
