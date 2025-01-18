package com.example.demo.serivce.image;

import com.example.demo.exceptions.ImageAlreadyExistsException;
import com.example.demo.exceptions.ImageNotFoundException;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.model.dto.ImageDto;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.Product;
import com.example.demo.model.mapping.ImageMapper;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ImageDto> addImage(List<MultipartFile> request, Long productId) {
        return productRepository.findById(productId)
                .map(product -> createImage(request, product))
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }
    private List<ImageDto> createImage(List<MultipartFile> request, Product product) {
        List<ImageDto> imagesDto =new ArrayList<>();
          request.forEach(file -> {
              imagesDto.add(ImageMapper.toDto(saveImage(file,product)));
          } );
        return imagesDto;
    }

    private Image saveImage(MultipartFile file, Product product)  {
        // Logic to save image to the database
        Image saveImage= null;
        try {
            Image image=new Image();
            image.setProduct(product);
            image.setTitle(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
            String urlImage="/api/v1/images/image/download/";
            String url=urlImage+image.getProduct().getName()+image.getId();
            image.setUrl(url);
            saveImage = imageRepository.save(image);
            saveImage.setUrl(url+saveImage.getProduct().getName()+saveImage.getId());
            imageRepository.save(saveImage);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return saveImage;
    }

    @Override
    public ImageDto getImage(Long id) {
        return imageRepository.findById(id)
                .map(ImageMapper::toDto)
                .orElseThrow(
                ()-> new ImageNotFoundException("Image Not Found")
        );
    }

    @Override
    public void updateImage(List<MultipartFile> request, Long id) {
         imageRepository.findById(id)
                .map(existingImage -> updateExistingImages(request, existingImage))// Update and return images
                .orElseThrow(() -> new ImageNotFoundException("Image Not Found")); // Throw exception if not found
    }


    private List<ImageDto> updateExistingImages(List<MultipartFile> request, Image existingImage) {
        List<ImageDto> imagesDto = new ArrayList<>();
        request.forEach(file -> {
            imagesDto.add(ImageMapper.toDto(saveImage(file,existingImage)));
        });
         return imagesDto;
    }

    private Image saveImage(MultipartFile file, Image existingImage)  {
        // Logic to save image to the database
        Image saveImage= null;
        try {
            existingImage.setTitle(file.getOriginalFilename());
            existingImage.setImage(new SerialBlob(file.getBytes()));
            String urlImage="/api/v1/images/image/download/";
            String url=urlImage+existingImage.getProduct().getName()+existingImage.getId();
            existingImage.setUrl(url);
            saveImage = imageRepository.save(existingImage);
            saveImage.setUrl(url+saveImage.getProduct().getName()+saveImage.getId());
            imageRepository.save(saveImage);
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
        return saveImage;
    }


    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(
                imageRepository :: delete,
                ()-> {throw new ImageNotFoundException("Image NOT Found");}
        );

    }

    @Override
    public ImageDto getImageByUrl(String url) {
        return ImageMapper.toDto(imageRepository.getImageByUrl(url));
    }


    @Override
    public List<ImageDto> getAllImages() {
        return imageRepository.findAll()
                .stream().map(ImageMapper::toDto)
                .toList();
    }

    @Override
    public List<ImageDto> getImagesByProduct(String productName) {
        return imageRepository.findImageByProductName(productName).stream()
                .map(ImageMapper::toDto)
                .toList();
    }

    @Override
    public Long getImageCountByProduct(String productName) {
        return imageRepository.countByProductName(productName);
    }
}
