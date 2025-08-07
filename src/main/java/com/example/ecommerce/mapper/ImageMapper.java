package com.example.ecommerce.mapper;

import com.example.ecommerce.dto.ImageDTO;
import com.example.ecommerce.entity.Image;
import com.example.ecommerce.request.image.AddImageRequest;
import com.example.ecommerce.request.image.UpdateImageRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between Image entity and ImageDTO.
 * It also handles conversion from AddImageRequest and UpdateImageRequest to Image entity.
 */
@Mapper(componentModel = "spring")
public interface ImageMapper{

    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    ImageDTO toDto(Image image);

    Image toEntity(AddImageRequest request);

    Image toEntity(UpdateImageRequest request);

    ImageDTO toDtoFromRequest(AddImageRequest request);

    ImageDTO toDtoFromRequest(UpdateImageRequest request);

}