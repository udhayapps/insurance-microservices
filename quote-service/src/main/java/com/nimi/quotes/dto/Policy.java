package com.nimi.quotes.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Policy implements Serializable
{
  private static final long serialVersionUID = -8341387007194361042L;

  String policyReference;

}
