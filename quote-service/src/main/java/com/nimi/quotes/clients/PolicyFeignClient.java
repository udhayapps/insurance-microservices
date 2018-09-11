package com.nimi.quotes.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.nimi.quotes.dto.Policy;

@FeignClient(value = "sidecar", fallback = PolicyFallback.class)
public interface PolicyFeignClient
{

  @GetMapping("/convert/{quote-reference}")
  Policy convertQuoteToPolicy(@PathVariable("quote-reference") String quoteReference);

}
