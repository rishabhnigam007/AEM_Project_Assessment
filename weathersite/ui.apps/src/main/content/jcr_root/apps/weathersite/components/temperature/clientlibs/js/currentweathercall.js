;
(function () {

    function CurrentWeather() {

        var getTodaysWeather = function () {
            var city = $('.city-name').val()
            $.ajax({
                type: "GET",
                url: "/apps/weather/api/v1/currentweather?q=" + city,
                success: function (response) {
                    console.log(response);

                },
                error: function (response) {
                    console.log(response);
                    alert("oops something went to wrong");
                }
            });
        }

        this.init = function () {
            getTodaysWeather()
        }
    }

    function ForeCast5DayWeather() {

        var get5dayWeather = function () {
            var city = $('.city-name').val()
            $.ajax({
                type: "GET",
                url: "/apps/weather/api/v1/forecast?q=" + city,
                success: function (response) {
                    console.log(response);

                },
                error: function (response) {
                    console.log(response);
                    alert("oops something went to wrong");
                }
            });
        }

        this.init = function () {
            get5dayWeather()
        }

    }

    if ($('.temprature-component') && $('.temprature-component').length) {
        new CurrentWeather().init();
        new ForeCast5DayWeather().init();
    }

})