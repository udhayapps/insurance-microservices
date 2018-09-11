package com.nimi.quotes.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quote")
public class Quote implements Serializable
{
  private static final long serialVersionUID = 3031176540031318190L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @Getter
  @Setter
  private Long id;

  @Column(name = "quote_reference", nullable = false)
  @Getter
  @Setter
  private String quoteReference;

  @Column(name = "policy_reference", nullable = true)
  @Getter
  @Setter
  private String policyReference;

  @Column(name = "premium", columnDefinition = "DECIMAL(10,2)", nullable = true)
  @Getter
  @Setter
  private BigDecimal premium;

  @Column(name = "excess", columnDefinition = "DECIMAL(10,2)", nullable = true)
  @Getter
  @Setter
  private BigDecimal excess;

  @JsonManagedReference("quote")
  @OneToMany(mappedBy = "quote", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @Getter
  @Setter
  private List<Risk> risks = new ArrayList<>();
}
