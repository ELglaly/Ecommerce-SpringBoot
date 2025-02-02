package com.example.demo.serivce.image;


import com.example.demo.constants.ApiConstants;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.dto.ImageDto;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.Product;

import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public ImageService(ImageRepository imageRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.imageRepository = imageRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ImageDto> addImage(List<MultipartFile> request, Long productId) {
        return productRepository.findById(productId)
                .map(product -> createImage(request, product))
                .orElseThrow(() -> new ResourceNotFoundException("Product not found","Product"));
    }
    private List<ImageDto> createImage(List<MultipartFile> request, Product product) {
        List<ImageDto> imagesDto =new ArrayList<>();
          request.forEach(file -> {
              Image image = saveImage(file,product);
              ImageDto imageDto = modelMapper.map(image, ImageDto.class);
              imagesDto.add(imageDto);
          } );
        return imagesDto;
    }

    private Image saveImage(MultipartFile file, Product product)  {
        // Logic to save image to the database
        try {
            String urlImage= ApiConstants.URL_IMAGES;
            Image image=new Image.Builder()
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
                .map(image -> modelMapper.map(image, ImageDto.class))
                .orElseThrow(
                ()-> new ResourceNotFoundException("Image Not Found","Product")
        );
    }

    @Override
    public void updateImage(List<MultipartFile> request, Long id) {
         imageRepository.findById(id)
                .map(existingImage -> updateExistingImages(request, existingImage))// Update and return images
                .orElseThrow(() -> new ResourceNotFoundException("Image Not Found","Product")); // Throw exception if not found
    }


    private List<ImageDto> updateExistingImages(List<MultipartFile> request, Image existingImage) {
        List<ImageDto> imagesDto = new ArrayList<>();
        request.forEach(file -> {
            Image image = saveUpdatedImage(file,existingImage);
            imagesDto.add(modelMapper.map(image, ImageDto.class));
        });
         return imagesDto;
    }

    private Image saveUpdatedImage(MultipartFile file, Image existingImage)  {
        // Logic to save image to the database
        Image saveImage= null;
        try {
            existingImage.setname(file.getOriginalFilename());
            existingImage.setImage(new SerialBlob(file.getBytes()));
            String urlImage=ApiConstants.URL_IMAGES;
            String url=urlImage+existingImage.getProduct().getName()+existingImage.getId();
            existingImage.setUrl(url);
            existingImage = imageRepository.save(existingImage);
            return existingImage;
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(
                imageRepository :: delete,
                ()-> {throw new ResourceNotFoundException("Image NOT Found","Product");}
        );

    }

    @Override
    public ImageDto getImageByUrl(String url) {

        return modelMapper.map(imageRepository.getImageByUrl(url), ImageDto.class);
    }


    @Override
    public List<ImageDto> getAllImages() {
        return imageRepository.findAll()
                .stream().map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
    }

    @Override
    public List<ImageDto> getImagesByProduct(String productName) {
        return imageRepository.findImageByProductName(productName).stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
    }
}
