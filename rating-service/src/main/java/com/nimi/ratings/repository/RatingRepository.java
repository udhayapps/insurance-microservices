package com.nimi.ratings.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.nimi.ratings.model.Rating;

import reactor.core.publisher.Flux;

@Repository
public interface RatingRepository extends ReactiveCrudRepository<Rating, Long>
{

  Flux<Rating> getRatingsByMakeAndModel(String make, String model);

  Flux<Rating> getRatingsByAge(Integer age);

  Flux<Rating> getRatingsByYearsLicenceHeld(Integer yearsLicenceHeld);

  Flux<Rating> getRatingsByMakeAndPostcodeAndYearsLicenceHeld(String make, String postcode, Integer yearsLicenceHeld);

}
