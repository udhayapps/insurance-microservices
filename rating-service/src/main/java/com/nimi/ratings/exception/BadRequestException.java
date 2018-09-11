package com.nimi.ratings.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException
{

  private static final long serialVersionUID = 172485139606446162L;

  public BadRequestException()
  {
    super();
  }

  private String message;

  public BadRequestException(String message)
  {
    this.message = message;
  }

  @Override
  public String getMessage()
  {
    return this.message;
  }

}
