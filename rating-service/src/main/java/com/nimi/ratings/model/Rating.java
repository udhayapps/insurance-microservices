package com.nimi.ratings.model;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "rating")
public class Rating extends BaseEntity
{
  private static final long serialVersionUID = 817540448687003868L;

  private String make;

  private String model;

  private Integer yearManufactured;

  private Integer yearsLicenceHeld;

  private String transmission;

  private String postcode;

  private Integer age;

  private Boolean isAnyClaims;

  private Boolean isAnyConvictions;

  private Boolean isAnyChildren;

  private Boolean isHomeOwner;

  private BigDecimal premium;

  private BigDecimal excess;

}
