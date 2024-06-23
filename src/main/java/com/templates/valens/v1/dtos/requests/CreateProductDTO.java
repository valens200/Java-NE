package com.templates.valens.v1.dtos.requests;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductDTO {
    @NotNull
    private String productName;
    @NotNull
    private int cost;
    @NotNull
    private String description;
    @NotNull
    private String imageUrl;
}
