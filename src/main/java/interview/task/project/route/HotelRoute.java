package interview.task.project.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HotelRoute extends RouteBuilder {

    @Value("${hotel.api.baseurl}")
    private  String BASE_URL;
    @Value("${hotel.api.key}")
    private String API_KEY;


    @Override
    public void configure() {
        errorHandler(defaultErrorHandler()
                .maximumRedeliveries(3) // Maximum number of redelivery attempts
                .redeliveryDelay(1000) // Delay between redelivery attempts in milliseconds
                .retryAttemptedLogLevel(LoggingLevel.ERROR)); // Log level for retry attempts

        onException(Exception.class)
                .handled(true) // Mark the exception as handled
                .log(LoggingLevel.ERROR, "Error calling API: ${exception.message}")
                .maximumRedeliveries(0); // Disable redelivery for API errors

        from("direct:hotelData")
                .setHeader("X-RapidAPI-Key", constant(API_KEY))
                .toD(BASE_URL+"?q=${header.cityName}")
                .convertBodyTo(String.class);
                //   .unmarshal().json();
    }
}
