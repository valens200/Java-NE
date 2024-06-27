package rca.ne.java.v1.dtos.requests;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
public class CreateProductDTO {
    @NotBlank(message = "Product name should not be empty")
    private String productName = "";

    @Positive(message = "The cost should be a positive integer")
    @Range(min = 1, message = "Please select positive numbers Only")
    private Integer cost;
    @NotBlank(message = "Description should not be empty")
    private String description = "";
    @NotBlank(message = "Image url should not be blank")
    private String imageUrl = "";
    @Future(message = "The date is invalid")
    private Date date = new Date();

    @NotBlank(message = "The national ID should not be empty")
    @Pattern(regexp = "[0-9]{16}", message = "Your national ID is invalid, you should use 16 numbers")
    private String nationalId;

    @Pattern(regexp = "(?:\\+2507|07)\\d{8}|\\+250\\d{10}", message = "Your phone is not a valid tel we expect 2507***, or 07*** or 7***")
    private String phoneNumber;
}
