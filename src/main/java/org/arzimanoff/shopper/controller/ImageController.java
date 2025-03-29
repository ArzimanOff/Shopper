package org.arzimanoff.shopper.controller;

import org.arzimanoff.shopper.dto.ImageDto;
import org.arzimanoff.shopper.exceptions.ResourceNotFoundException;
import org.arzimanoff.shopper.model.Image;
import org.arzimanoff.shopper.response.ApiResponse;
import org.arzimanoff.shopper.service.image.ImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImages(
            @RequestParam List<MultipartFile> files, // точно ли не @RequestBody?
            @RequestParam Long productId
    ){
        try {
            List<ImageDto> imageDtos = imageService.saveImages(files, productId);
            return ResponseEntity.ok(
                    new ApiResponse("Фотографии успешно сохранены!", imageDtos)
            );
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Ошибка сохранения файлов!", e.getMessage()));
        }
    }

    // Скачивание изображения по ID
    @GetMapping("/{imageId}/download")
    public ResponseEntity<Resource> downloadImage(
            @PathVariable Long imageId
    ) throws SQLException {
        Image image = imageService.getImageById(imageId);

        ByteArrayResource resource =
                new ByteArrayResource(
                        image.getImage().getBytes(1, (int) image.getImage().length())
                );

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(
            @RequestBody MultipartFile file,
            @PathVariable Long imageId
    ){
        try {
            Image image = imageService.getImageById(imageId);

            if (image != null){
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Фото успешно обновлено!", null));
            }
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Ошибка обновления фотки.", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(
            @PathVariable Long imageId
    ){
        try {
            Image image = imageService.getImageById(imageId);

            if (image != null){
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new ApiResponse("Фото успешно удалено!", null));
            }
        } catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), null));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Ошибка удаления фотки.", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
