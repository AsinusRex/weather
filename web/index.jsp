<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Weather Page</title>

</head>
<body>



<h1>Get Weather</h1>
<input type="text" id="cityInput" placeholder="Type in city">
<input type="submit" id="citySubmit">

<br>

<div id="results"></div>


<script>
    function getWeather(city) {
        let reqBody = JSON.stringify({city:city});
        let resultsDiv = document.getElementById("results");
        let xhr = new XMLHttpRequest();
        xhr.onreadystatechange = () => {
            if (xhr.readyState === 4 && xhr.status === 200) {
                resultsDiv.innerHTML= xhr.responseText;
            }
        };

        xhr.open("POST", "./getWeather", true);
        xhr.setRequestHeader("Content-type", "application/json");

        xhr.send(reqBody);
    }

    let submitBtn = document.getElementById("citySubmit");
    let submitInput = document.getElementById("cityInput");
    submitBtn.addEventListener("click", () => {
        getWeather(submitInput.value);
    });
</script>
</body>
</html>
