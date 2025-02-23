package com.example.Ecommerce.controller;


import com.example.Ecommerce.constants.ApiConstants;
import com.example.Ecommerce.model.dto.ImageDto;
import com.example.Ecommerce.response.ApiResponse;
import com.example.Ecommerce.serivce.image.IImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.IMAGE_ENDPOINT)
public class ImageController {
    private final IImageService imageService;
    public ImageController(IImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> addImages(@PathVariable Long productId,
                                              @RequestParam List<MultipartFile> file) {
        List<ImageDto> imageDtos =imageService.addImage(file,productId);

        return ResponseEntity.ok(new ApiResponse(imageDtos,"Added Successfully"));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse> getImageById(@PathVariable Long id) {
        ImageDto imageDto = imageService.getImage(id);
        return ResponseEntity.ok(new ApiResponse(imageDto, "Image Retrieved Successfully"));
    }

    @PutMapping("/product/{productId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long productId,
                                                           @RequestParam List<MultipartFile> files) {
        imageService.updateImage(files, productId);
        return ResponseEntity.ok(new ApiResponse("Updated Successfully", "Success"));
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.ok(new ApiResponse("Deleted Successfully", "Success"));
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAllImages() {
        List<ImageDto> imageDtos = imageService.getAllImages();
        return ResponseEntity.ok(new ApiResponse(imageDtos, "All Images Retrieved Successfully"));
    }

    @GetMapping("/product-mame/{productName}")
    public ResponseEntity<ApiResponse> getImagesByProduct(@RequestParam String productName) {
        List<ImageDto> imageDtos = imageService.getImagesByProduct(productName);
        return ResponseEntity.ok(new ApiResponse(imageDtos, "Images Retrieved Successfully"));
    }

//    @GetMapping("/count/{productName}")
//    public ResponseEntity<ApiResponse> getImageCountByProduct(@PathVariable String productName) {
//        Long count = imageService.getImageCountByProduct(productName);
//        return ResponseEntity.ok(new ApiResponse(count, "Image Count Retrieved Successfully"));
//    }
}
