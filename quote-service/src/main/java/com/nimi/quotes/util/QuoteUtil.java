package com.nimi.quotes.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class QuoteUtil
{

  public static String generateQuoteReference()
  {
    MessageDigest salt = null;
    try
    {
      salt = MessageDigest.getInstance("SHA-256");
      salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
    }
    catch (NoSuchAlgorithmException | UnsupportedEncodingException ex)
    {
      log.error("Error: generateQuoteReference(): {0}", ex);
    }
    return bytesToHex(salt.digest());
  }

  private static String bytesToHex(byte[] hash)
  {
    StringBuffer hexString = new StringBuffer();
    for (int i = 0; i < hash.length; i++)
    {
      String hex = Integer.toHexString(0xff & hash[i]);
      if (hex.length() == 1)
        hexString.append('0');
      hexString.append(hex);
    }
    return hexString.toString();
  }

}
