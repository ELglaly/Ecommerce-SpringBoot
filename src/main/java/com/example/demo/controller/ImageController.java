package com.example.demo.controller;


import com.example.demo.constants.ApiConstants;
import com.example.demo.model.dto.ImageDto;
import com.example.demo.response.ApiResponse;
import com.example.demo.serivce.image.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.IMAGE_ENDPOINT)
public class ImageController {
    private final ImageService imageService;
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/upload/{productId}")
    public ResponseEntity<ApiResponse> addImages(@PathVariable("productId") Long productId,
                                              @RequestParam List<MultipartFile> file) {
        List<ImageDto> imageDtos =imageService.addImage(file,productId);

        return ResponseEntity.ok(new ApiResponse(imageDtos,"Added Successfully"));
    }

    @GetMapping("get/{id}")
    public ResponseEntity<ApiResponse> getImageById(@PathVariable Long id) {
        ImageDto imageDto = imageService.getImage(id);
        return ResponseEntity.ok(new ApiResponse(imageDto, "Image Retrieved Successfully"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long id,
                                                           @RequestParam("files") List<MultipartFile> files) {
        imageService.updateImage(files, id);
        return ResponseEntity.ok(new ApiResponse("Updated Successfully", "Success"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long id) {
        imageService.deleteImage(id);
        return ResponseEntity.ok(new ApiResponse("Deleted Successfully", "Success"));
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAllImages() {
        List<ImageDto> imageDtos = imageService.getAllImages();
        return ResponseEntity.ok(new ApiResponse(imageDtos, "All Images Retrieved Successfully"));
    }

    @GetMapping("get/product/{productName}")
    public ResponseEntity<ApiResponse> getImagesByProduct(@PathVariable String productName) {
        List<ImageDto> imageDtos = imageService.getImagesByProduct(productName);
        return ResponseEntity.ok(new ApiResponse(imageDtos, "Images Retrieved Successfully"));
    }

    @GetMapping("/count/{productName}")
    public ResponseEntity<ApiResponse> getImageCountByProduct(@PathVariable String productName) {
        Long count = imageService.getImageCountByProduct(productName);
        return ResponseEntity.ok(new ApiResponse(count, "Image Count Retrieved Successfully"));
    }
}
