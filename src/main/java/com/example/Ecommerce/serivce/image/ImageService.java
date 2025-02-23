package com.example.Ecommerce.serivce.image;


import com.example.Ecommerce.constants.ApiConstants;
import com.example.Ecommerce.exceptions.image.ImageNotFoundException;
import com.example.Ecommerce.exceptions.product.ProductNotFoundException;
import com.example.Ecommerce.mapper.ImageMapper;
import com.example.Ecommerce.model.dto.ImageDto;
import com.example.Ecommerce.model.entity.Image;
import com.example.Ecommerce.model.entity.Product;

import com.example.Ecommerce.repository.ImageRepository;
import com.example.Ecommerce.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ImageMapper imageMapper;

    public ImageService(ImageRepository imageRepository, ProductRepository productRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.imageMapper = imageMapper;
    }

    @Override
    public List<ImageDto> addImage(List<MultipartFile> request, Long productId) {
        return productRepository.findById(productId)
                .map(product -> createImage(request, product))
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
    private List<ImageDto> createImage(List<MultipartFile> request, Product product) {
        List<ImageDto> imagesDto =new ArrayList<>();
          request.forEach(file -> {
              Image image = saveImage(file,product);
              ImageDto imageDto = imageMapper.toDto(image);
              imagesDto.add(imageDto);
          } );
        return imagesDto;
    }

    private Image saveImage(MultipartFile file, Product product)  {
        // Logic to save image to the database
        try {
            String urlImage= ApiConstants.URL_IMAGES;
            Image image= Image.builder()
                    .image(new SerialBlob(file.getBytes()))
                    .product(product).name(file.getOriginalFilename())
                    .build();
            String url=urlImage+image.getProduct().getName()+image.getId();
            image.setUrl(url);
            image = imageRepository.save(image);
            return image;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ImageDto getImage(Long id) {
        return imageRepository.findById(id)
                .map(imageMapper::toDto)
                .orElseThrow(
                ()-> new ProductNotFoundException("Image Not Found")
        );
    }

    @Override
    public List<ImageDto> updateImage(List<MultipartFile> request, Long productId) {
        Product product= productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
         imageRepository.deleteByProductId(productId);
         List<ImageDto> imagesDto =new ArrayList<>();
        request.forEach(file -> {
            Image image = saveImage(file,product);
            imagesDto.add(imageMapper.toDto(image));
        } );
        return imagesDto;
    }



    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(
                imageRepository :: delete,
                ()-> {throw new ProductNotFoundException("Image NOT Found");}
        );

    }

    @Override
    public ImageDto getImageByUrl(String url) {

        return Optional.ofNullable(imageRepository.getImageByUrl(url))
                .map(imageMapper::toDto)
                .orElseThrow(()-> new ImageNotFoundException("Image Not Found"));
    }


    @Override
    public List<ImageDto> getAllImages() {
        return imageRepository.findAll()
                .stream().map(imageMapper::toDto)
                .toList();
    }

    @Override
    public List<ImageDto> getImagesByProduct(String productName) {
        return imageRepository.findImageByProductName(productName).stream()
                .map(imageMapper::toDto)
                .toList();
    }
}
