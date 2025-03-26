package org.arzimanoff.shopper.request;

import lombok.Data;
import org.arzimanoff.shopper.model.Category;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
