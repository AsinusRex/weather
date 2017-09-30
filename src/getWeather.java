import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.stream.Collectors;

@WebServlet(name = "getWeather")
public class getWeather extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String city = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        String appKey = "71f14444ca1e87ceedff4e52f4b2a9af";

        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&APPID=" + appKey;

        URLConnection urlConnection = new URL(url).openConnection();
        InputStream responseFromAPI = urlConnection.getInputStream();
        Scanner scanner = new Scanner(responseFromAPI);
        String responseToPage = scanner.useDelimiter("\\A").next();


        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseToPage);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
