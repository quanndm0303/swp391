package com.app.zware.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.util.Date;
import lombok.Data;

@Entity(name = "items")
@Data
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer product_id;
  private LocalDate expire_date;
  private Boolean isdeleted = false;// Default to false for new item
}
