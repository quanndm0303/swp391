package com.app.zware.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.dao.DataAccessException;

import java.util.Date;

@Entity(name="outboundtransactions")
@Data
public class OutboundTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Date date;
    private Integer maker_id;
    private String status;
    private Integer destination;
    private String external_destination;
}
