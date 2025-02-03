package com.example.demo.mapper;

import com.example.demo.model.dto.ImageDto;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.request.product.AddProductRequest;
import com.example.demo.request.product.UpdateProductRequest;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper implements IProductMapper {
    private final ModelMapper modelMapper;
    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product toEntity(AddProductRequest request) {
        return modelMapper.map(request, Product.class);
    }

    public Product toEntity(ProductDto request) {
        return modelMapper.map(request, Product.class);
    }

    public ProductDto toDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<ImageDto> imageDto = product.getImages().stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .collect(Collectors.toList());
        productDto.setImageDto(imageDto);
        return productDto;
    }

    public Product toEntity(UpdateProductRequest request) {
        return null;
    }
}
