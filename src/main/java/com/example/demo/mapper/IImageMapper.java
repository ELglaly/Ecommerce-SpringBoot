package com.example.demo.mapper;

import com.example.demo.model.dto.ImageDto;
import com.example.demo.model.entity.Image;
import com.example.demo.request.image.AddImageRequest;
import com.example.demo.request.image.UpdateImageRequest;

public interface IImageMapper extends IDtoToEntityMapper<Image, ImageDto>
                           ,IEntityToDtoMapper<Image, ImageDto>
    ,IAddRequestToEntityMapper<Image, AddImageRequest>,
        IUpdateRequestToEntityMapper<Image, UpdateImageRequest>
{
}
