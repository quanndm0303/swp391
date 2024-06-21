package com.app.zware.Entities;


import jakarta.persistence.*;
import lombok.Data;


@Entity(name = "warehouseitems")
@Data
public class WarehouseItems {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private Integer zone_id;
  private Integer item_id;
  private Integer quantity;
  private Boolean isdeleted = false; //Default to false for new warehouse item

}
