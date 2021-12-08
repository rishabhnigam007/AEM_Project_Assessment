$("#city-name, #country-id").on('blur', function () {

    if ($('#city-name').val() != "" && $('#country-id').val() != "") {
        fetchCurrentWeather();
        fetchFiveDayWeather();
    }
});

$("#latitude, #longitude").on('blur', function () {

    if ($('#latitude').val() != "" && $('#longitude').val() != "") {
        fetchFivehourWeather();
    }
});

function fetchCurrentWeather() {
    var city = $('#city-name').val()
    var country = $('#country-id').val()
    $.ajax({
        type: "GET",
        url: "/apps/weather/api/v1/currentweather?q=" + city + "&countryid=" + country,
        contentType: "application/json",
        success: function (data) {
            try {
                var json = JSON.parse(data);
                if (json) {
                    $('#current-temperature .currentTemp').text("Current temperature : " + Math.round((json.main.temp - 273) * 100) / 100 + " " + String.fromCharCode(176) + "c");
                    $('#current-temperature .maxTemp').text("Maximum temperature : " + Math.round((json.main.temp_max - 273) * 100) / 100 + " " + String.fromCharCode(176) + "c");
                    $('#current-temperature .minTemp').text("Minimum temperature : " + Math.round((json.main.temp_min - 273) * 100) / 100 + " " + String.fromCharCode(176) + "c");
                    $('#current-temperature .humidity').text("Humidity : " + json.main.humidity);
                    $('#forecast5day-temperature .sunrise').text("Sunrise time : " + new Date(json.sys.sunrise * 1000).toLocaleTimeString());
                    $('#forecast5day-temperature .sunset').text("Sunset time : " + new Date(json.sys.sunset * 1000).toLocaleTimeString());
                }
                else {
                }
            } catch (e) { console.log('Error' + e); }
        },
        error: function (e) {
            alert("There is no data present for showing !!")
        }
    });
}

function fetchFiveDayWeather() {
    var city = $('#city-name').val()
    var country = $('#country-id').val()
    $.ajax({
        type: "GET",
        url: "/apps/weather/api/v1/forecast?q=" + city + "&countryid=" + country,
        contentType: "application/json",
        success: function (data) {
            try {
                var json = JSON.parse(data);
                if (json) {

                    $('#forecast5day-temperature .maxiTemp').text("Maximum temperature Day 1 : " + Math.round((json.list[0].main.temp_max - 273) * 100) / 100 + " " + String.fromCharCode(176) + "c");
                    $('#forecast5day-temperature .miniTemp').text("Minimum temperature Day 1 : " + Math.round((json.list[0].main.temp_min - 273) * 100) / 100 + " " + String.fromCharCode(176) + "c");
                    $('#forecast5day-temperature .humidities').text("Humidity Day 1 : " + json.list[0].main.humidity);

                    $('#forecast5day-temperature .maxiTemp2').text("Maximum temperature Day 2 : " + Math.round((json.list[1].main.temp_max - 273) * 100) / 100 + " " + String.fromCharCode(176) + "c");
                    $('#forecast5day-temperature .miniTemp2').text("Minimum temperature Day 2 : " + Math.round((json.list[1].main.temp_min - 273) * 100) / 100 + " " + String.fromCharCode(176) + "c");
                    $('#forecast5day-temperature .humidities2').text("Humidity Day 2 : " + json.list[1].main.humidity);

                    $('#forecast5day-temperature .maxiTemp3').text("Maximum temperature Day 3 : " + Math.round((json.list[2].main.temp_max - 273) * 100) / 100 + " " + String.fromCharCode(176) + "c");
                    $('#forecast5day-temperature .miniTemp3').text("Minimum temperature Day 3 : " + Math.round((json.list[2].main.temp_min - 273) * 100) / 100 + " " + String.fromCharCode(176) + "c");
                    $('#forecast5day-temperature .humidities3').text("Humidity : " + json.list[2].main.humidity);

                    $('#forecast5day-temperature .maxiTemp4').text("Maximum temperature Day 4 : " + Math.round((json.list[3].main.temp_max - 273) * 100) / 100 + " " + String.fromCharCode(176) + "c");
                    $('#forecast5day-temperature .miniTemp4').text("Minimum temperature Day 4 : " + Math.round((json.list[3].main.temp_min - 273) * 100) / 100 + " " + String.fromCharCode(176) + "c");
                    $('#forecast5day-temperature .humidities4').text("Humidity Day 4 : " + json.list[3].main.humidity);

                    $('#forecast5day-temperature .maxiTemp5').text("Maximum temperature Day 5 : " + Math.round((json.list[4].main.temp_max - 273) * 100) / 100 + " " + String.fromCharCode(176) + "c");
                    $('#forecast5day-temperature .miniTemp5').text("Minimum temperature Day 5 : " + Math.round((json.list[4].main.temp_min - 273) * 100) / 100 + " " + String.fromCharCode(176) + "c");
                    $('#forecast5day-temperature .humidities5').text("Humidity Day 5 : " + json.list[4].main.humidity);
                }
                else {

                }
            } catch (e) { console.log('Error' + e); }
        },
        error: function (e) {
            alert("There is no data present for showing !!")
        }
    });
}

function fetchFivehourWeather() {
    var latitude = $('#latitude').val()
    var longitude = $('#longitude').val()
    $.ajax({
        type: "GET",
        url: "/apps/weather/api/v1/onecall?lat=" + latitude + "&lon=" + longitude,
        contentType: "application/json",
        success: function (data) {
            try {
                var json = JSON.parse(data);
                if (json) {
                    $('#onedayforecast-temperature .maxiiTemp').text("Maximum temperature : " + Math.round((json.hourly[0].temp - 273) * 100) / 100 + " " + String.fromCharCode(176) + "c");
                    $('#onedayforecast-temperature .miniiTemp').text("Minimum temperature : " + Math.round((json.hourly[0].temp - 273) * 100) / 100 + " " + String.fromCharCode(176) + "c");
                    $('#onedayforecast-temperature .humiditi').text("Humidity : " + json.hourly[0].humidity);
                    $('#onedayforecast-temperature .sunrises').text("Sunrise time : " + new Date(json.current.sunrise * 1000).toLocaleTimeString());
                    $('#onedayforecast-temperature .sunsets').text("Sunset time : " + new Date(json.current.sunset * 1000).toLocaleTimeString());
                }
                else {
                }
            } catch (e) { console.log('Error' + e); }
        },
        error: function (e) {
            alert("There is no data present for showing !!")
        }
    });
}