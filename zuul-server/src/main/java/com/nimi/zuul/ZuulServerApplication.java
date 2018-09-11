package com.nimi.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.nimi.zuul.filters.RequestPreFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableCircuitBreaker
@EnableHystrixDashboard
public class ZuulServerApplication
{
  public static void main(String[] args)
  {
    SpringApplication.run(ZuulServerApplication.class, args);
  }

  @Bean
  public RequestPreFilter preFilter()
  {
    return new RequestPreFilter();
  }
  
  /*@Bean
  public ResponsePostFilter postFilter()
  {
    return new ResponsePostFilter();
  }*/
}
