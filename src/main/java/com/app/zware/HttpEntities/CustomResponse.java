package com.app.zware.HttpEntities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomResponse {

  private Boolean success;
  private String message;
  private Object data;

  public void setAll(Boolean success, String message, Object data) {
    this.success = success;
    this.message = message;
    this.data = data;
  }
}
