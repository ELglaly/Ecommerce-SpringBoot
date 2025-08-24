package com.example.ecommerce.serivce.image;

import com.example.ecommerce.dto.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageManagementService {
    List<ImageDTO> addImage(List<MultipartFile> image, Long productId);
    List<ImageDTO> updateImage(List<MultipartFile> image, Long id);
    void deleteImage(Long id);
}
