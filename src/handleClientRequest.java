import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

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
        //Extracts City name from request
        String city = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        //Passes city name to make API call
        String responseToPage = getDataFromAPI.weatherData(city);

        //Responds to the page
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        response.getWriter().write(responseToPage);
    }
}
