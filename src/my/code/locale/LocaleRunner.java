package my.code.locale;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleRunner {
    public static void main(String[] args) {
        Locale locale = Locale.forLanguageTag("en_US");
        System.out.println(Locale.US);
        System.out.println(Locale.getDefault());

        ResourceBundle rb = ResourceBundle.getBundle("translations", locale);
        System.out.println(rb.getString("page.login.password"));
    }
}
