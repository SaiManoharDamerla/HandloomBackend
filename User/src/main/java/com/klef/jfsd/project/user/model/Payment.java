package com.klef.jfsd.project.user.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "payments")
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int payment_id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    private String paymentMethod;
    private String paymentStatus;
    private Date paymentDate;
    private Double amount;

    // Getters and Setters
}
