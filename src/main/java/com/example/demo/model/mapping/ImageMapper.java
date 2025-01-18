package com.example.demo.model.mapping;

import com.example.demo.model.dto.ImageDto;
import com.example.demo.model.entity.Image;

public class ImageMapper {

    public static Image toEntity(ImageDto imageDto) {
        return Image.builder()
                .url(imageDto.getUrl())
                .id(imageDto.getId())
                .title(imageDto.getTitle())
                .product(ProductMapper.toEntity(imageDto.getProductDTO()))
                .image(imageDto.getImage())
                .build();
    }

    public static ImageDto toDto(Image image) {
        return ImageDto.builder()
                .url(image.getUrl())
                .id(image.getId())
                .title(image.getTitle())
                .productDTO(ProductMapper.toDto(image.getProduct()))
                .image(image.getImage())
                .build();
    }
}
