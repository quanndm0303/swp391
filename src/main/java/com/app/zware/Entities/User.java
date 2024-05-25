package com.app.zware.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.Data;

@Entity(name = "users")
@Data

public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(unique = true)
  private String email;
  private String password;
  private String name;
  private String role;
  private Date date_of_birth;
  private String phone;
  private String gender;
  private String avatar;
  private int warehouse_id;


}

