import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.Properties;

@WebServlet(name = "handleClientRequest")
public class handleClientRequest extends HttpServlet {
    /**
     * Handles the request from the web page and passes the name of the city to the getDataFromAPI class' weatherData method
     *
     * @param request  represents the HTTP request with the JSON data in the body coming from the page
     * @param response represents the response the servlet gives to the page, including the stringified JSON with the data
     * @see getDataFromAPI
     **/
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

//Get Properties file
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("config.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        String OWappKey = properties.getProperty("OWAppKey");
        String OWBaseURL = properties.getProperty("OWBaseURL");
        String GMAppKey = properties.getProperty("GMAppKey");
        String GMBaseURL = properties.getProperty("GMBaseURL");

        //Extracts City name from request
        String city = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        //Passes city name to make API call
        String weatherDataFromAPI = getDataFromAPI.weatherData(city, OWappKey, OWBaseURL);

        //Responds to the page
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String modifiedAPIData = weatherDataFromAPI.substring(0, weatherDataFromAPI.length() - 1);

String responseToPage = weatherDataFromAPI + ",{\"appKey\":\"" + GMAppKey + "\"},{\"appURL\":\"" + GMBaseURL + "\"}";

        response.getWriter().write(responseToPage);
    }
}
