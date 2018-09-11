package com.nimi.quotes.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "risk")
public class Risk implements Serializable
{
  private static final long serialVersionUID = 7393968555965630888L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Getter
  @Setter
  private Long id;

  @JsonBackReference("quote")
  @ManyToOne(optional = false)
  @JoinColumn(name = "quote_id")
  @Getter
  @Setter
  private Quote quote;

  @Column(name = "cover_start_date", nullable = false)
  @Getter
  @Setter
  @NotNull
  private Date coverStartDate;

  @Column(name = "cover_level", nullable = false)
  @Getter
  @Setter
  @NotNull
  private String coverLevel;

  @Column(name = "risk_type", nullable = false)
  @Getter
  @Setter
  @NotNull
  private String riskType;

  @Column(name = "year_bought", nullable = false)
  @Getter
  @Setter
  @NotNull
  private Integer yearBought;

  @Column(name = "month_bought", nullable = false)
  @Getter
  @Setter
  @NotNull
  private Integer monthBought;

  @Column(name = "annual_mileage", nullable = false)
  @Getter
  @Setter
  @NotNull
  private Integer annualMileage;

  @Column(name = "voluntary_excess", nullable = false)
  @Getter
  @Setter
  @NotNull
  private Integer voluntaryExcess;

  @Column(name = "years_no_claims_bonus_held", nullable = false)
  @Getter
  @Setter
  @NotNull
  private Integer yearsNoClaimsBonusHeld;

  @Column(name = "registration", nullable = false)
  @Getter
  @Setter
  @NotNull
  private String registration;

  @Column(name = "make", nullable = false)
  @Getter
  @Setter
  @NotNull
  private String make;

  @Column(name = "model", nullable = false)
  @Getter
  @Setter
  @NotNull
  private String model;

  @Column(name = "year_manufactured", nullable = false)
  @Getter
  @Setter
  @NotNull
  private Integer yearManufactured;

  @Column(name = "fuel_type", nullable = false)
  @Getter
  @Setter
  @NotNull
  private String fuelType;

  @Column(name = "transmission", nullable = false)
  @Getter
  @Setter
  @NotNull
  private String transmission;

  @Column(name = "full_name", nullable = false)
  @Getter
  @Setter
  @NotNull
  private String fullName;

  @Column(name = "licence_type", nullable = false)
  @Getter
  @Setter
  @NotNull
  private String licenceType;

  @Column(name = "years_licence_held", nullable = false)
  @Getter
  @Setter
  @NotNull
  private Integer yearsLicenceHeld;
  
  @Column(name = "occupation", nullable = false)
  @Getter
  @Setter
  @NotNull
  private String occupation;

  @Column(name = "address", nullable = false)
  @Getter
  @Setter
  @NotNull
  private String address;

  @Column(name = "location", nullable = false)
  @Getter
  @Setter
  @NotNull
  private String location;

  @Column(name = "postcode", nullable = false)
  @Getter
  @Setter
  @NotNull
  private String postcode;

  @Column(name = "email", nullable = false)
  @Getter
  @Setter
  @NotNull
  private String email;

  @Column(name = "phone", nullable = false)
  @Getter
  @Setter
  @NotNull
  private String phone;

  @Column(name = "is_home_owner", nullable = false)
  @Getter
  @Setter
  @NotNull
  private Boolean isHomeOwner;

  @Column(name = "is_any_children", nullable = false)
  @Getter
  @Setter
  @NotNull
  private Boolean isAnyChildren;

  @Column(name = "is_any_claims", nullable = false)
  @Getter
  @Setter
  @NotNull
  private Boolean isAnyClaims;

  @Column(name = "is_any_convictions", nullable = false)
  @Getter
  @Setter
  @NotNull
  private Boolean isAnyConvictions;

  @Column(name = "is_gdpr_consented", nullable = false)
  @Getter
  @Setter
  @NotNull
  private Boolean isGdprConsented;

}
