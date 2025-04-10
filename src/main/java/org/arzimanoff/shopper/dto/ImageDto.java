package org.arzimanoff.shopper.dto;

import lombok.Data;

@Data
public class ImageDto {
    private Long id;
    private String imageName;
    private String downloadUrl;
}
