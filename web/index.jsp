<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Weather Page</title>

</head>
<body>


<h1>Search for a city below to get the weather</h1>

<input type="text" id="cityInput" placeholder="Type in city">
<input type="submit" id="citySubmit">


<br>
<br>

<div id="results"></div>


<script>
    /*Page Elements*/
    let resultsDiv = document.getElementById("results");
    let submitBtn = document.getElementById("citySubmit");
    let submitInput = document.getElementById("cityInput");

    //Format response
    function displayResponse(responseText) {
        resultsDiv.innerHTML = "";
        let responseJson = JSON.parse(responseText);

        //Create Error message
        if (responseJson.hasOwnProperty("errMsg")) {
            resultsDiv.innerHTML = responseJson.errMsg;
            resultsDiv.style.color = "#FF0000";
        }

        //Create data display
        else {

            resultsDiv.style.color = "#000000";
            let coordinates = responseJson.coord.lat + "," + responseJson.coord.lon;

            let elementsArray = [];
            let cityName =  document.createElement("H2");
            cityName.innerText = submitInput.value + ", " + responseJson.sys.country;
            let cityTitle = document.createElement("H2");
            cityTitle.innerText = responseJson.name;
            let weatherCondition = document.createElement("H3");
            weatherCondition.innerText = responseJson.weather[0].main;
            let weatherDescription = document.createElement("H4");
            weatherDescription.innerText =responseJson.weather[0].description;
            let weatherIcon = document.createElement("IMG");
            weatherIcon.setAttribute("src", "http://openweathermap.org/img/w/" + responseJson.weather[0].icon + ".png");
            let temperature = document.createElement("DIV");
            temperature.innerText = "Current Temperature " + responseJson.main.temp;
            let maxTemperature = document.createElement("DIV");
            maxTemperature.innerText = "Maximum Temperature " + responseJson.main.temp_max;
            let minTemperature = document.createElement("DIV");
            minTemperature.innerText = "Minimum Temperature " + responseJson.main.temp_min;
            let windSpeed = document.createElement("DIV");
            windSpeed.innerText = "Wind Speed " + responseJson.wind.speed + " km/h";
            let windDirection = document.createElement("IMG");
            windDirection.setAttribute("src", "./img/compass-rose.gif");
            windDirection.style.transform = "rotate(" + responseJson.wind.deg + "deg)";
            let map = document.createElement("IMG");
            map.setAttribute("src", "https://maps.googleapis.com/maps/api/staticmap?center=" + coordinates + "&markers=color:blue=%7C"+coordinates+"&zoom=5&size=600x300&maptype=hybrid&key=AIzaSyAtKWPjORxOFfanjbXsI4YI-wNCUi3zKcE");




            elementsArray.push(cityName, cityTitle, weatherCondition, weatherDescription, weatherIcon,temperature, maxTemperature, minTemperature, windSpeed, windDirection, map);

            elementsArray.forEach(function (element) {
                resultsDiv.appendChild(element);

            });



        }

    }

    //AJAX call
    function getWeather(city) {
        //The body to send to the web service
        let reqBody = JSON.stringify({city: city});


        //Initialize xhr
        let xhr;
        //For normal browsers
        if (window.XMLHttpRequest) {
            xhr = new XMLHttpRequest();
        }
        //Sigh... IE
        else {
            xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }

        /* Handle success, error and timeout */
        //Error messages
        const errorMsg = "{errMsg: We have encountered a problem retrieving your data, please try again later}";
        const timeoutMsg = "{errMsg: Your request has timed out, please try again}";
        xhr.onreadystatechange = () => {
            if (xhr.readyState === 4) {
                switch (xhr.status) {
                    case 200:
                        displayResponse(xhr.responseText);
                        break;
                    case 500:
                        displayResponse(errorMsg);
                        break;
                }
            }
        };
        xhr.onerror = () => {
            displayResponse(errorMsg);
        };
        xhr.ontimeout = () => {
            displayResponse(timeoutMsg);
        };

        //XHR setup
        xhr.timeout = 5000;
        xhr.open("POST", "./handleClientRequest", true);
        xhr.setRequestHeader("Content-type", "application/json");
        xhr.send(reqBody);
    }


    //Listeners
    submitBtn.addEventListener("click", () => {
        getWeather(submitInput.value);
    });
    submitInput.addEventListener("keyup", function(event) {
        event.preventDefault();
        if (event.keyCode === 13) {
           submitBtn.click();
        }
    });

</script>
</body>
</html>
