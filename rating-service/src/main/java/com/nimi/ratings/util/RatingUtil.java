package com.nimi.ratings.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class RatingUtil
{

  public static Optional<String> getRealIp()
  {
    URL ipChecker = null;
    BufferedReader bufferedReader = null;
    String realIp = null;
    try
    {
      ipChecker = new URL("http://checkip.amazonaws.com");
      bufferedReader = new BufferedReader(new InputStreamReader(ipChecker.openStream()));
      realIp = bufferedReader.readLine();
    }
    catch (IOException ex)
    {
      log.error("Error: getRealIp(): {0}", ex);
      return Optional.empty();
    }
    return Optional.of(realIp);
  }

}
