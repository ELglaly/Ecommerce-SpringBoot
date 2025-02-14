package com.example.demo.mapper;

import com.example.demo.model.dto.ImageDto;
import com.example.demo.model.entity.Image;
import com.example.demo.request.image.AddImageRequest;
import com.example.demo.request.image.UpdateImageRequest;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper implements IImageMapper {
    private final ModelMapper modelMapper;
    public ImageMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public Image toEntityFromAddRequest(AddImageRequest addRequest) {
        return modelMapper.map(addRequest, Image.class);
    }

    @Override
    public Image toEntityFromDto(ImageDto dto) {
        return modelMapper.map(dto, Image.class);
    }

    @Override
    public ImageDto toDto(Image entity) {
        return modelMapper.map(entity, ImageDto.class);
    }

    @Override
    public Image toEntityFromUpdateRequest(UpdateImageRequest updateRequest) {
        return modelMapper.map(updateRequest, Image.class);
    }
}
