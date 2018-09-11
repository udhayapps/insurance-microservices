package com.nimi.quotes.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Rating implements Serializable
{

  private static final long serialVersionUID = -8807270845060062643L;

  private BigDecimal premium;
  private BigDecimal excess;
}
