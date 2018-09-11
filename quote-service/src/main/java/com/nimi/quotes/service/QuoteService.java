package com.nimi.quotes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nimi.quotes.model.Quote;
import com.nimi.quotes.repository.QuoteRepository;

@Service
@Transactional(readOnly = true)
public class QuoteService
{

  @Autowired
  QuoteRepository quoteRepository;

  public Optional<Quote> getQuoteByQuoteReference(String quoteReference)
  {
    return quoteRepository.findByQuoteReference(quoteReference);
  }

  public Optional<Quote> getQuoteById(Long id)
  {
    return quoteRepository.findById(id);
  }

  public List<Quote> getQuotes()
  {
    return quoteRepository.findAll();
  }

  @Transactional
  public Quote saveQuote(Quote quote)
  {
    return quoteRepository.save(quote);
  }
}
