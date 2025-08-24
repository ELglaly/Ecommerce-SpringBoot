package com.example.ecommerce.serivce.image;

import com.example.ecommerce.dto.ImageDTO;

import java.util.List;

public interface IImageSearchService {
    ImageDTO getImage(Long id);
    ImageDTO getImageByUrl(String url);
    List<ImageDTO> getAllImages();
    List<ImageDTO> getImagesByProduct(String product);
}
