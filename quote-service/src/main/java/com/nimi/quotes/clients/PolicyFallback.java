package com.nimi.quotes.clients;

import org.springframework.stereotype.Component;

import com.nimi.quotes.dto.Policy;

@Component
public class PolicyFallback implements PolicyFeignClient
{

  @Override
  public Policy convertQuoteToPolicy(String quoteReference)
  {
    return new Policy("POL-ERROR");
  }

}
