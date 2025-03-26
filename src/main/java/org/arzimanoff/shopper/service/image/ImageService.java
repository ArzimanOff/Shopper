package org.arzimanoff.shopper.service.image;

import org.arzimanoff.shopper.dto.ImageDto;
import org.arzimanoff.shopper.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);

}
