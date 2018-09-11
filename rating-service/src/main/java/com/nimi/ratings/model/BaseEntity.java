package com.nimi.ratings.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseEntity implements Serializable
{

  private static final long serialVersionUID = -5832346679755956413L;

  @Id
  private String id;

  @CreatedBy
  private String createdBy;

  @CreatedDate
  private Date createdDate;

  @LastModifiedBy
  private String updatedBy;

  @LastModifiedDate
  private Date updatedDate;

}
