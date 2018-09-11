package com.nimi.quotes.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nimi.quotes.clients.PolicyFeignClient;
import com.nimi.quotes.clients.RatingWebClient;
import com.nimi.quotes.dto.Policy;
import com.nimi.quotes.dto.Rating;
import com.nimi.quotes.exception.BadRequestException;
import com.nimi.quotes.model.Quote;
import com.nimi.quotes.service.QuoteService;
import com.nimi.quotes.util.QuoteUtil;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController("QuoteController-v1")
@RequestMapping(path = "quotes", produces = "application/vnd.com.nimi.quotes.v1+json")
@ResponseStatus(HttpStatus.OK)
public class QuoteController
{
  
  @Autowired
  QuoteService quoteService;
  
  @Autowired
  RatingWebClient ratingWebClient;
  
  @Autowired
  PolicyFeignClient policyFeignClient;

  @PostMapping
  public void createQuote(@RequestBody @Valid final Quote quote)
  {
    log.info("Quote createQuote()");

    quote.setQuoteReference("QTE-" + QuoteUtil.generateQuoteReference().substring(0, 15).toUpperCase());

    if (CollectionUtils.isNotEmpty(quote.getRisks()))
    {
      quote.getRisks().forEach(risk -> {
        if (risk.getRiskType() != null && risk.getRiskType().equalsIgnoreCase("CAR"))
        {
          Rating rating = getRatingsByMakePostcodeYearsLicenceHeld(risk.getMake(), risk.getPostcode(), risk.getYearsLicenceHeld());
          if (rating != null)
          {
            quote.setPremium(rating.getPremium());
            quote.setExcess(rating.getExcess());
          }
        }
        else
        {
          // TODO: Implement Rating for Multi-Risk Quotes
        }
      });
    }
    else
    {
      throw new BadRequestException("No RISK data! Atleast one risk is expected on Quote");
    }

    Quote resultQuote = quoteService.saveQuote(quote);
    log.info("Quote createQuote() Result::Quote-ID: {}", resultQuote.getId());
  }
  
  @PatchMapping("/{id}")
  public void convertQuoteToPolicy(@PathVariable("id") final Long id)
  {
    log.info("Quote convertQuoteToPolicy({})", id);

    Quote quote = getQuoteById(id);
    if (quote != null && !StringUtils.isBlank(quote.getQuoteReference()))
    {
      Policy policy = convertQuoteToPolicy(quote.getQuoteReference());
      quote.setPolicyReference(policy.getPolicyReference());
      quoteService.saveQuote(quote);
    }
    else
    {
      throw new BadRequestException("Unable to update Quote for [id]: " + id);
    }
  }
  
  @GetMapping
  public List<Quote> getQuotes()
  {
    log.info("Quote getQuotes()");
    return quoteService.getQuotes();
  }

  @GetMapping("/{id}")
  public Quote getQuoteById(@PathVariable("id") final Long id)
  {
    log.info("Quote getQuoteById({})", id);
    Quote quote = quoteService.getQuoteById(id)
                              .orElseThrow(() -> new BadRequestException("Unable to return Quote for [id]: " + id));
    return quote;
  }

  @GetMapping("/quote-reference/{quote-reference}")
  public Quote getQuoteByReference(@PathVariable("quote-reference") final String quoteReference)
  {
    log.info("Quote getQuoteByReference({})", quoteReference);
    return quoteService.getQuoteByQuoteReference(quoteReference)
                       .orElseThrow(() -> new BadRequestException("Unable to return Quote for [quote-reference]: " + quoteReference));
  }
  
  @GetMapping("/convert/{quote-reference}")
  public Policy convertQuoteToPolicy(@PathVariable("quote-reference") final String quoteReference)
  {
    log.info("Policy convertQuoteToPolicy({})", quoteReference);

    // Call Sidecar(Policy Service) via PolicyFeignClient(Blocking)
    return policyFeignClient.convertQuoteToPolicy(quoteReference);
  }

  @GetMapping("/rating")
  public Rating getRatingsByMakePostcodeYearsLicenceHeld(@RequestParam("make") final String make,
                                                         @RequestParam("postcode") final String postcode, 
                                                         @RequestParam("yearsLicenceHeld") final Integer yearsLicenceHeld)
  {
    log.info("Quote getRatingsByMakePostcodeYearsLicenceHeld({}, {}, {})", make, postcode, yearsLicenceHeld);

    // Call Rating Service via RatingWebClient(Reactive)
    Flux<Rating> ratings = ratingWebClient.getRatingsByMakePostcodeYearsLicenceHeld(make, postcode, yearsLicenceHeld)
                                          .switchIfEmpty(Mono.error(new BadRequestException("Unable to return ratings")));

    // Calculate Averages of Premium/Excess as Ratings received from Rating-Service is a Flux
    List<BigDecimal> premiums = new ArrayList<>();
    List<BigDecimal> excess = new ArrayList<>();
    
    // Reactive to Blocking Extraction: Block the Flux and Collect to List
    List<Rating> ratingsList = ratings.collectList().block();
    if (CollectionUtils.isNotEmpty(ratingsList))
    {
      ratingsList.forEach(rating -> {
        premiums.add(rating.getPremium());
        excess.add(rating.getExcess());
      });
    }

    BigDecimal totalPremium = premiums.stream()
                                      .map(Objects::requireNonNull)
                                      .reduce(BigDecimal.ZERO, BigDecimal::add);
    
    BigDecimal totalExcess = excess.stream()
                                   .map(Objects::requireNonNull)
                                   .reduce(BigDecimal.ZERO, BigDecimal::add);
    
    Rating resultRating = new Rating();
    resultRating.setPremium(totalPremium.divide(new BigDecimal(premiums.size()), BigDecimal.ROUND_HALF_UP));
    resultRating.setExcess(totalExcess.divide(new BigDecimal(excess.size()), BigDecimal.ROUND_HALF_UP));
    
    return resultRating;
  }
  
  @GetMapping("/ratings")
  public Flux<Rating> getRatings()
  {
    return ratingWebClient.getRatings();
  }

}
