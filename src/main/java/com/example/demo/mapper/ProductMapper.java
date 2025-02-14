package com.example.demo.mapper;

import com.example.demo.model.dto.CategoryDto;
import com.example.demo.model.dto.ImageDto;
import com.example.demo.model.dto.ProductDto;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.request.product.AddProductRequest;
import com.example.demo.request.product.UpdateProductRequest;
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

    @Override
    public ProductDto toDto(Product product) {

        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        if (product.getImages() != null) {
            List<ImageDto> imageDto = product.getImages().stream()
                    .map(image -> modelMapper.map(image, ImageDto.class))
                    .collect(Collectors.toList());
            productDto.setImageDto(imageDto);
        }
        productDto.setCategoryDto(modelMapper.map(product.getCategory(), CategoryDto.class));
        return productDto;
    }

    @Override
    public Product toEntityFromDto(ProductDto dto) {
        return modelMapper.map(dto, Product.class);
    }

    @Override
    public Product toEntityFromUpdateRequest(UpdateProductRequest request) {
        return modelMapper.map(request, Product.class);
    }

    @Override
    public Product toEntityFromAddRequest(AddProductRequest addRequest) {
        return modelMapper.map(addRequest, Product.class);
    }
}
