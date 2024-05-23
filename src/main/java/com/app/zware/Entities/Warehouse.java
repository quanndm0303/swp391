package com.app.zware.Entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "warehouses")
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String address;
    @OneToMany(mappedBy = "warehouse" ,cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<User> userList;

    @OneToMany(mappedBy = "warehouse",fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<WarehouseZone> warehouseZones;
}
