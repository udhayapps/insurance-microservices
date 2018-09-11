package com.nimi.ratings.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimi.ratings.model.Rating;
import com.nimi.ratings.repository.RatingRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional(readOnly = true)
public class RatingService
{

  @Autowired
  RatingRepository ratingRepository;

  public Flux<Rating> getRatings()
  {
    return ratingRepository.findAll();
  }

  public Flux<Rating> getRatingsByMakeAndModel(String make, String model)
  {
    return ratingRepository.getRatingsByMakeAndModel(make, model);
  }

  public Flux<Rating> getRatingsByAge(Integer age)
  {
    return ratingRepository.getRatingsByAge(age);
  }

  public Flux<Rating> getRatingsByYearsLicenceHeld(Integer yearsLicenceHeld)
  {
    return ratingRepository.getRatingsByYearsLicenceHeld(yearsLicenceHeld);
  }

  public Flux<Rating> getRatingsByMakeAndPostcodeAndYearsLicenceHeld(String make, String postcode, Integer yearsLicenceHeld)
  {
    return ratingRepository.getRatingsByMakeAndPostcodeAndYearsLicenceHeld(make, postcode, yearsLicenceHeld);
  }

  @Transactional
  public Mono<Rating> createRating(Rating rating)
  {
    return ratingRepository.save(rating);
  }

}
