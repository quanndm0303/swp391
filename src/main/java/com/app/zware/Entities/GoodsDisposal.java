package com.app.zware.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity(name = "goodsdisposal")
@Data
public class GoodsDisposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer warehouse_id;
    private Integer maker_id;
    private Date date;
    private String status;
}
