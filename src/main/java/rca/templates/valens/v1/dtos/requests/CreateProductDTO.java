package rca.templates.valens.v1.dtos.requests;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CreateProductDTO {
    @javax.validation.constraints.NotNull(message = "The product name is required")
    @NotBlank
    private String productName;

    private int cost;
    @javax.validation.constraints.NotNull(message = "The product description is required")
    @NotBlank
    private String description;
    private String imageUrl;
}
