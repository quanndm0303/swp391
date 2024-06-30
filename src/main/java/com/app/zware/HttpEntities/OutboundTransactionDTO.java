package com.app.zware.HttpEntities;

import java.time.LocalDate;
import lombok.Data;

@Data
public class OutboundTransactionDTO {
  private Integer warehouse_id;
  private Integer destination;
  private String external_destination;

}
