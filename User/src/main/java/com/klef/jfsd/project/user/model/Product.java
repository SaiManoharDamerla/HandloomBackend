package com.klef.jfsd.project.user.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int product_id;

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "seller_id")
    private Seller seller;

    private String productName;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String categoryName;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String image;

}
