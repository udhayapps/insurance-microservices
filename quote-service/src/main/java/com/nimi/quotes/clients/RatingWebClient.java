package com.nimi.quotes.clients;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.HystrixCommands;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.nimi.quotes.dto.Rating;

import reactor.core.publisher.Flux;

@Service
public class RatingWebClient
{

  @Autowired
  private WebClient.Builder webClientBuilder;

  @HystrixCommand(fallbackMethod = "getRatingsByMakePostcodeYearsLicenceHeldFallback",
                  commandProperties = {
                     @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
                  }
  )
  public Flux<Rating> getRatingsByMakePostcodeYearsLicenceHeld(String make, String postcode, Integer yearsLicenceHeld)
  {
    WebClient webClient = webClientBuilder.baseUrl("http://rating-service/ratings")
                                          .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.com.nimi.ratings.v1+json")
                                          .defaultHeader(HttpHeaders.USER_AGENT, "quote-service")
                                          .build();
    
    Flux<Rating> rating =  webClient.get()
                                    .uri(uriBuilder -> uriBuilder.path("/rating")
                                                                 .queryParam("make", make)
                                                                 .queryParam("postcode", postcode)
                                                                 .queryParam("yearsLicenceHeld", yearsLicenceHeld)
                                                                 .build())
                                    .retrieve()
                                    .bodyToFlux(Rating.class);
    
    return HystrixCommands.from(rating)
                          .fallback(getRatingsByMakePostcodeYearsLicenceHeldFallback(make, postcode, yearsLicenceHeld))
                          .commandName("getRatingsByMakePostcodeYearsLicenceHeldFallback")
                          .toFlux();
  }
  
  public Flux<Rating> getRatingsByMakePostcodeYearsLicenceHeldFallback(String make, String postcode, Integer yearsLicenceHeld)
  {
    return Flux.just(new Rating(new BigDecimal(1000.00), new BigDecimal(100.00)));
  }

  @HystrixCommand(fallbackMethod = "getRatingsFallback")
  public Flux<Rating> getRatings()
  {
    WebClient webClient = webClientBuilder.baseUrl("http://rating-service/ratings")
                                          .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.com.nimi.ratings.v1+json")
                                          .defaultHeader(HttpHeaders.USER_AGENT, "quote-service")
                                          .build();
    
    Flux<Rating> rating = webClient.get()
                                   .retrieve()
                                   .bodyToFlux(Rating.class);
    
    return HystrixCommands.from(rating)
                          .fallback(getRatings())
                          .commandName("getRatingsFallback")
                          .toFlux();
  }

  public Flux<Rating> getRatingsFallback()
  {
    return Flux.just(new Rating(new BigDecimal(999.99), new BigDecimal(99.99)));
  }

}
