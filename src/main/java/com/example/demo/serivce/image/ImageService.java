package com.example.demo.serivce.image;

import com.example.demo.dto.ImageDTO;
import com.example.demo.exceptions.ImageAlreadyExistsException;
import com.example.demo.exceptions.ImageNotFoundException;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.model.entity.Image;
import com.example.demo.model.entity.Product;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.request.AddImageRequest;
import com.example.demo.request.UpdateImageRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    public Image addImage(List<MultipartFile> request, Long productId) {
        return productRepository.findById(productId)
                .map(product -> createImage(request,product)).map(imageRepository::save)
                .orElseThrow(()-> new ProductNotFoundException("Product not found"));
    }

    private Image createImage(List<MultipartFile> request, Product product) {
        List<ImageDTO> images=new ArrayList<>();

        for (MultipartFile file : request) {
            try {
                Image image=new Image();
                image.setProduct(product);
                image.setTitle(file.getOriginalFilename());
                image.setImage(new SerialBlob(file.getBytes()));
                String urlImage="/api/v1/images/image/download/";
                String url=urlImage+image.getProduct().getName()+image.getId();
                image.setUrl(url);
                Image saveImage= imageRepository.save(image);
                saveImage.setUrl(url+saveImage.getProduct().getName()+saveImage.getId());
                imageRepository.save(saveImage);
                ImageDTO imageDto=new ImageDTO();
                imageDto.setUrl(saveImage.getUrl());
                imageDto.setImageName(saveImage.getTitle());
                imageDto.setId(saveImage.getId());
                images.add(imageDto);
            } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

    @Override
    public Image getImage(Long id) {
        return imageRepository.findById(id).orElseThrow(
                ()-> new ImageNotFoundException("Image Not Found")
        );
    }

    @Override
    public void updateImage(List<MultipartFile> request, Long id) {
         imageRepository.findById(id)
                .map(existingImage -> updateExistingImages(request, existingImage))
                 .map(imageRepository ::saveAll) // Update and return images
                .orElseThrow(() -> new ImageNotFoundException("Image Not Found")); // Throw exception if not found
    }


    private List<Image> updateExistingImages(List<MultipartFile> request, Image existingImage) {
        List<Image> images = new ArrayList<>();

        for (MultipartFile file : request) {
            try {
                // Create a new image or update the existing one
                Image image = new Image();
                image.setProduct(existingImage.getProduct()); // Keep the same product
                image.setTitle(file.getOriginalFilename()); // Set title from file name

                // Generate a new URL for the image
                String urlImage = "/api/v1/images/image/download/";
                image.setUrl(urlImage + existingImage.getProduct().getName() + image.getId());

                // Set image blob from file bytes
                image.setImage(new SerialBlob(file.getBytes()));
                images.add(image);
            } catch (SQLException | IOException e) {
                throw new RuntimeException("Error processing file: " + file.getOriginalFilename(), e);
            }
        }

        // Save all the new or updated images to the repository
         return imageRepository.saveAll(images);
    }


    @Override
    public void deleteImage(Long id) {
        imageRepository.findById(id).ifPresentOrElse(
                imageRepository :: delete,
                ()-> {throw new ImageNotFoundException("Image NOT Found");}
        );

    }

    @Override
    public Image getImageByUrl(String url) {
        return imageRepository.getImageByUrl(url);
    }


    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public List<Image> getImagesByProduct(String productName) {
        return imageRepository.findImageByProductName(productName);
    }

    @Override
    public Long getImageCountByProduct(String productName) {
        return imageRepository.countByProductName(productName);
    }
}
