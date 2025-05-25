package my.code.http.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlExample {
    public static void main(String[] args) throws IOException {
        var url = new URL("file:src\\my\\code\\http\\socket\\DatagramRunner.java");
        var urlConnection = url.openConnection();

        System.out.println(new String(urlConnection.getInputStream().readAllBytes()));
    }

    private static void readPage() throws IOException {
        var url = new URL("https://www.google.com");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    private static void checkGoogle() throws IOException {
        var url = new URL("https://www.google.com");
        var urlConnection = url.openConnection();

        var content = urlConnection.getContent();
        System.out.println(content);
        urlConnection.getHeaderFields().forEach((key, value) -> System.out.println(key + " : " + value));
        System.out.println(new String(urlConnection.getInputStream().readAllBytes()));

        try (var outputStream = urlConnection.getOutputStream()) {
        }
    }
}
