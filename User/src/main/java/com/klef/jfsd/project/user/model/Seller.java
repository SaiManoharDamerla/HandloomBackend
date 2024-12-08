package com.klef.jfsd.project.user.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sellers")
@Data
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seller_id;

    private String email;
    private String password;
    private String storeName;
    private String storeAddress;
    private String gstNo;


}
