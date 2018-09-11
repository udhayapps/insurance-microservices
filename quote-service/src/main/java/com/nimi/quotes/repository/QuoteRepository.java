package com.nimi.quotes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nimi.quotes.model.Quote;

@RepositoryRestResource
public interface QuoteRepository extends JpaRepository<Quote, Long>
{

  Optional<Quote> findByQuoteReference(String quoteReference);

}
