package com.icf.weathersite.core.service;

import com.icf.weathersite.core.climate48hours.Climate48HoursResponse;
import com.icf.weathersite.core.data.ApiResponse;
import com.icf.weathersite.core.fivedayforecast.FiveDayForecastResponse;

public interface WeatherService {

    ApiResponse getCurrentWeather(String cityName);
    FiveDayForecastResponse getDailyForecastFor16Days(String cityName);
    Climate48HoursResponse getClimateForecastFor30Days(String latitude, String longitude);

}