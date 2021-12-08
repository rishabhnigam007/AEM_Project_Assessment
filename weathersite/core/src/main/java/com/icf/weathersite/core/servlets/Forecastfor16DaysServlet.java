package com.icf.weathersite.core.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.icf.weathersite.core.data.ApiError;
import com.icf.weathersite.core.data.ApiResponse;
import com.icf.weathersite.core.fivedayforecast.FiveDayForecastResponse;
import com.icf.weathersite.core.service.WeatherService;
import org.apache.http.HttpStatus;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;

/**
 * Servlet that writes some sample content into the response. It is mounted for
 * all resources of a specific Sling resource type. The
 * {@link SlingSafeMethodsServlet} shall be used for HTTP methods that are
 * idempotent. For write operations use the {@link SlingAllMethodsServlet}.
 */
@Component(service = { Servlet.class }, property = {
        "sling.servlet.paths=/apps/weather/api/v1/forecast"
})
@ServiceDescription("Servlet for getting the daily forecast 5 days Weather for a city by name")
public class Forecastfor16DaysServlet extends SlingSafeMethodsServlet{

    private static final long serialVersionUID = 1L;
    private Logger logger = LoggerFactory.getLogger(Forecastfor16DaysServlet.class.getName());

    @Reference
    public WeatherService weatherService;

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp) throws IOException {
        String cityName = req.getParameter("q");

        ObjectMapper mapper = new ObjectMapper();
        logger.info("calling weatherservice for forecast weather 5 day for city : {}", cityName);
        FiveDayForecastResponse apiResponse = weatherService.getDailyForecastFor16Days(cityName);
        logger.debug("got api response for forecast weather 5 day for city : {} \n {}", cityName, apiResponse);
        if(apiResponse != null){
            logger.info("writing response to api call for forecast weather 5 day for city : {}", cityName);
            resp.getWriter().write(mapper.writeValueAsString(apiResponse));
        }else{
            logger.error("received null for forecast weather 5 day for city : {}", cityName);
            //set the response code
            resp.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            //construct ApiError object
            ApiError apiError = new ApiError(HttpStatus.SC_INTERNAL_SERVER_ERROR, "There was an internal server error");
            //write the error response to the request
            resp.getWriter().write(mapper.writeValueAsString(apiError));
        }
    }

}
