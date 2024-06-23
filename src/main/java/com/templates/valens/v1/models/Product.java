package com.templates.valens.v1.models;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private int cost;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String imageUrl;
}
