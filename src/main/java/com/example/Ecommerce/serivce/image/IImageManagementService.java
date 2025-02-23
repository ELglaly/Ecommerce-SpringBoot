package com.example.Ecommerce.serivce.image;

import com.example.Ecommerce.model.dto.ImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageManagementService {
    List<ImageDto> addImage(List<MultipartFile> image, Long productId);
    List<ImageDto> updateImage(List<MultipartFile> image, Long id);
    void deleteImage(Long id);
}
