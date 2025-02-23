package com.example.Ecommerce.mapper;

import com.example.Ecommerce.model.dto.ImageDto;
import com.example.Ecommerce.model.entity.Image;
import com.example.Ecommerce.request.image.AddImageRequest;
import com.example.Ecommerce.request.image.UpdateImageRequest;

public interface IImageMapper extends IDtoToEntityMapper<Image, ImageDto>
                           ,IEntityToDtoMapper<Image, ImageDto>
    ,IAddRequestToEntityMapper<Image, AddImageRequest>,
        IUpdateRequestToEntityMapper<Image, UpdateImageRequest>
{
}
