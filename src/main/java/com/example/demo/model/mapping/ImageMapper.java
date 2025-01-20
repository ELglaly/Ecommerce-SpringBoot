package com.example.demo.model.mapping;

import com.example.demo.model.dto.ImageDto;
import com.example.demo.model.entity.Image;

public class ImageMapper {

    public static Image toEntity(ImageDto imageDto) {
        return new Image.Builder()
                .url(imageDto.getUrl())
                .id(imageDto.getId())
                .name(imageDto.getname())
                .product(ProductMapper.toEntity(imageDto.getProductDTO()))
                .image(imageDto.getImage())
                .build();
    }

    public static ImageDto toDto(Image image) {
        return new ImageDto.Builder()
                .url(image.getUrl())
                .id(image.getId())
                .name(image.getname())
                .productDTO(ProductMapper.toDto(image.getProduct()))
                .image(image.getImage())
                .build();
    }
}
