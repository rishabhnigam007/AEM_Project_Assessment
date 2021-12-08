package com.icf.weathersite.core.service;

import com.drew.lang.annotations.NotNull;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icf.weathersite.core.climate48hours.Climate48HoursResponse;
import com.icf.weathersite.core.data.ApiResponse;
import com.icf.weathersite.core.fivedayforecast.FiveDayForecastResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


@Component(service = WeatherService.class,
        immediate = true)
@Designate(ocd = WeatherServiceImpl.Config.class)
public class WeatherServiceImpl implements WeatherService {

    private static final String SERVER = "localhost:4502";
    private static final String RESOURCE_PATH = "/content/OpenWeather";
    private String apikey = "";
    private String location = "";
    private ResourceResolver resourceResolver;

    private String currentWeatherEndpoint;
    private String dailyFOrecast16DaysEndpoint;
    private String climateForecast30daysEndpoint;

    private Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    /**
     * Configuration class for the OpenWeather API endpoints
     */
    @ObjectClassDefinition(name = "Configuration class for the Open Weather API")
    public static @interface Config {

        @AttributeDefinition(
                name = "Current weather endpoint",
                description = "API endpoint for current weather"
        )
        String currentWeatherEndpoint() default "https://api.openweathermap.org/data/2.5/weather";

        @AttributeDefinition(
                name = "16 days daily forecast endpoint",
                description = "API endpoint for daily forecast"
        )
        String dailyForecast16DaysEndpoint() default "https://api.openweathermap.org/data/2.5/forecast";

        @AttributeDefinition(
                name = "30 daays Climate forecast endpoint",
                description = "API endpoint for 30 days climate forecast"
        )
        String climateForecast30DaysEndpoint() default "https://api.openweathermap.org/data/2.5/onecall";

        @AttributeDefinition(
                name = "API key",
                description = "API key for consuming the Open weather API"
        )
        String apikey() default "9b5255153f1e0d4b0de53c1bae133728";

    }

    /**
     * This method is called when the OSGI bundle is installed and this service is activated.
     * The COnfiguration object is supplied as a parameter to the method from which the values
     * of the configuration can be obtained and assigned ot member variables
     * @param config
     */
    @Activate
    public void activate(Config config){
        this.apikey = config.apikey();
        this.currentWeatherEndpoint = config.currentWeatherEndpoint();
        this.dailyFOrecast16DaysEndpoint = config.dailyForecast16DaysEndpoint();
        this.climateForecast30daysEndpoint = config.climateForecast30DaysEndpoint();
    }

    @Override
    public ApiResponse getCurrentWeather(@NotNull  String cityName) {

        //build the endpoint url with required parameters
        StringBuilder builder = new StringBuilder();
        builder.append(currentWeatherEndpoint).append("?q=")
                .append(cityName).append("&appid=").append(apikey);

        //create a http client
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ApiResponse apiResponse = null;

        try {
            //create URI from the built url
            URI uri= new URI(builder.toString());

            //create a http get obnject
            HttpGet httpGet = new HttpGet(uri);
            //add application/json header
            httpGet.addHeader("Accept", "application/json");

            logger.info("atempting to hit the current weather api");

            try {
                //connect to the url
                HttpGet request = new HttpGet(uri);
                CloseableHttpResponse response = httpClient.execute(request);
                //get the response entity
                HttpEntity entity = response.getEntity();
                if(entity != null){
                    //convert the string response to ApiResponse object

                    apiResponse = new ObjectMapper().readValue(EntityUtils.toString(entity), ApiResponse.class);
                }
            } catch (ClientProtocolException e) {
               logger.error("Network error while calling the current weather api : {}", e.getMessage(), e);
            } catch (IOException e) {
                logger.error("error connecting to current weather api : {}", e.getMessage(), e);
            }
        } catch (URISyntaxException e) {
            logger.error("There was an error building the endpoint url : {}", e.getMessage(), e);
        }

        //return the ApiResponse object
        return apiResponse;

    }

    @Override
    public FiveDayForecastResponse getDailyForecastFor16Days(@NotNull  String cityName) {

        //build the endpoint url with required parameters
        StringBuilder builder = new StringBuilder();
        builder.append(dailyFOrecast16DaysEndpoint).append("?q=")
                .append(cityName).append("&appid=").append(apikey);

        //create a http client
        CloseableHttpClient httpClient = HttpClients.createDefault();
        FiveDayForecastResponse apiResponse = null;

        try {
            //create URI from the built url
            URI uri= new URI(builder.toString());

            //create a http get obnject
            HttpGet httpGet = new HttpGet(uri);
            //add application/json header
            httpGet.addHeader("Accept", "application/json");

            logger.info("atempting to hit the current weather api");

            try {
                //connect to the url
                HttpGet request = new HttpGet(uri);
                CloseableHttpResponse response = httpClient.execute(request);
                //get the response entity
                HttpEntity entity = response.getEntity();
                if(entity != null){
                    //convert the string response to ApiResponse object

                    apiResponse = new ObjectMapper().readValue(EntityUtils.toString(entity), FiveDayForecastResponse.class);
                }
            } catch (ClientProtocolException e) {
                logger.error("Network error while calling the current weather api : {}", e.getMessage(), e);
            } catch (IOException e) {
                logger.error("error connecting to current weather api : {}", e.getMessage(), e);
            }
        } catch (URISyntaxException e) {
            logger.error("There was an error building the endpoint url : {}", e.getMessage(), e);
        }

        //return the ApiResponse object
        return apiResponse;
    }

    @Override
    public Climate48HoursResponse getClimateForecastFor30Days(@NotNull String latitude, @NotNull String longitude) {
        //build the endpoint url with required parameters
        StringBuilder builder = new StringBuilder();
        builder.append(climateForecast30daysEndpoint).append("?lat=")
                .append(latitude).append("&lon=").append(longitude).append("&appid=").append(apikey);

        //create a http client
        CloseableHttpClient httpClient = HttpClients.createDefault();
        Climate48HoursResponse apiResponse = null;

        try {
            //create URI from the built url
            URI uri= new URI(builder.toString());

            //create a http get obnject
            HttpGet httpGet = new HttpGet(uri);
            //add application/json header
            httpGet.addHeader("Accept", "application/json");

            logger.info("atempting to hit the current weather api");

            try {
                //connect to the url
                HttpGet request = new HttpGet(uri);
                CloseableHttpResponse response = httpClient.execute(request);
                //get the response entity
                HttpEntity entity = response.getEntity();
                if(entity != null){
                    //convert the string response to ApiResponse object

                    apiResponse = new ObjectMapper().readValue(EntityUtils.toString(entity), Climate48HoursResponse.class);
                }
            } catch (ClientProtocolException e) {
                logger.error("Network error while calling the current weather api : {}", e.getMessage(), e);
            } catch (IOException e) {
                logger.error("error connecting to current weather api : {}", e.getMessage(), e);
            }
        } catch (URISyntaxException e) {
            logger.error("There was an error building the endpoint url : {}", e.getMessage(), e);
        }

        //return the ApiResponse object
        return apiResponse;
    }

}
