package com.example.Ecommerce.serivce.image;

import com.example.Ecommerce.model.dto.ImageDto;

import java.util.List;

public interface IImageSearchService {
    ImageDto getImage(Long id);
    ImageDto getImageByUrl(String url);
    List<ImageDto> getAllImages();
    List<ImageDto> getImagesByProduct(String product);
}
