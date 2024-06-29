package com.app.zware.HttpEntities;

import java.time.LocalDate;
import java.util.ArrayList;
import lombok.Data;

@Data
public class InboundTransactionDTO {
  private Integer warehouse_id;
  private LocalDate date;
  private Integer maker_id;
  //  private String status;
  private Integer source;
  private String external_source;

  private ArrayList<InboundDetailDTO> details;

}
