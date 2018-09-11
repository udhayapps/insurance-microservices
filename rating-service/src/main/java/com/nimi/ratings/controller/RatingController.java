package com.nimi.ratings.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nimi.ratings.exception.BadRequestException;
import com.nimi.ratings.model.Rating;
import com.nimi.ratings.service.RatingService;
import com.nimi.ratings.util.RatingUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController("RatingController")
@RequestMapping(path = "ratings", produces = "application/vnd.com.nimi.ratings.v1+json")
@ResponseStatus(HttpStatus.OK)
public class RatingController
{
  @Autowired
  RatingService ratingService;

  @GetMapping
  public Flux<Rating> getRatings()
  {
    log.info("Rating getRatings()");
    return ratingService.getRatings();
  }

  @GetMapping("/make/{make}/model/{model}")
  public Flux<Rating> getRatingsByMakeAndModel(@PathVariable("make") final String make, @PathVariable("model") final String model)
  {
    log.info("Rating getRatingsByMakeAndModel({}, {})", make, model);
    return ratingService.getRatingsByMakeAndModel(make, model)
                        .switchIfEmpty(Mono.error(new BadRequestException("Unable to return ratings by [make]: " + make + " OR [model]: " + model)));
  }

  @GetMapping("/age/{age}")
  public Flux<Rating> getRatingsByAge(@PathVariable("age") final Integer age)
  {
    log.info("Rating getRatingsByAge {}", age);
    return ratingService.getRatingsByAge(age)
                        .switchIfEmpty(Mono.error(new BadRequestException("Unable to return ratings by [age]: " + age)));
  }

  @GetMapping("/years-licence-held/{yearsLicenceHeld}")
  public Flux<Rating> getRatingsByYearsLicenceHeld(@PathVariable("yearsLicenceHeld") final Integer yearsLicenceHeld)
  {
    log.info("Rating getRatingsByYearsLicenceHeld {}", yearsLicenceHeld);
    return ratingService.getRatingsByYearsLicenceHeld(yearsLicenceHeld)
                        .switchIfEmpty(Mono.error(new BadRequestException("Unable to return ratings by [yearsLicenceHeld]: " + yearsLicenceHeld)));
  }
  
  @GetMapping("/rating")
  public Flux<Rating> getRatingsByMakePostcodeYearsLicenceHeld(@RequestParam("make") String make,
                                                               @RequestParam("postcode") String postcode, 
                                                               @RequestParam("yearsLicenceHeld") final Integer yearsLicenceHeld)
  {
    log.info("Rating getRatingsByMakePostcodeYearsLicenceHeld({}, {}, {})", make, postcode, yearsLicenceHeld);
    return ratingService.getRatingsByMakeAndPostcodeAndYearsLicenceHeld(make, postcode, yearsLicenceHeld)
                        .switchIfEmpty(Mono.error(new BadRequestException("Unable to return ratings!")));
  }

  @PostMapping
  public Mono<Rating> createRating(@RequestBody final Rating rating)
  {
    log.info("Rating createRating()");

    rating.setCreatedBy(RatingUtil.getRealIp().orElse(""));
    rating.setUpdatedBy(RatingUtil.getRealIp().orElse(""));
    return ratingService.createRating(rating);
  }

}
