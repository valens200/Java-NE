package rca.templates.valens.v1.models;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
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
