package app.yourdevbro.backend.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequestDto {

    private String headline;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private int stock;
}