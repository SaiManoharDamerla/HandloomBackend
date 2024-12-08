package com.klef.jfsd.project.user.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "shipping")
@Data
public class Shipping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int shipping_id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    private String shippingAddress;
    private Date shippingDate;
    private String trackingNumber;
    private String status;

    // Getters and Setters
}
