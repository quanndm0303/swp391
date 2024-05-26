package com.app.zware.Entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "disposedgoods")
@Data
public class DisposedGood {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int disposal_id;
    private int item_id;
    private int quantity;
    private String reason;
}
