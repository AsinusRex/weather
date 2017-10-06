import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

class getDataFromAPI {
    /**
     * This method makes a call to the Open Weather Map API to retrieve the data for a specific city
     *
     * @param city - the name of the city for the query string
     * @return The weather data for the city
     * @throws IOException if there is an issue accessing the web service
     **/
    static String weatherData(String city,  String OWappKey, String OWBaseURL) throws IOException {

        //URL for API
        String url = OWBaseURL + "?q=" + city + "&APPID=" + OWappKey + "&units=metric";

        //Attempt Connection
        try {
            URLConnection urlConnection = new URL(url).openConnection();
            InputStream responseFromAPI = urlConnection.getInputStream();
            Scanner scanner = new Scanner(responseFromAPI);
            return scanner.useDelimiter("\\A").next();
        }

        //Handle different issue types
        catch (Exception e) {
            String errorText;
            if (e.getClass().getSimpleName().equals("FileNotFoundException")) {
                errorText = "{\"errMsg\": \"The city you requested could not be found, please check your spelling\"}";
            } else {
                errorText = "{\"errMsg\": \"Cannot connect to webservice, please try again later\"}";
            }
            return errorText;
        }
    }
}
