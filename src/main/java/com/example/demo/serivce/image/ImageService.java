package com.example.demo.serivce.image;

import com.example.demo.exceptions.ImageAlreadyExistsException;
import com.example.demo.exceptions.ImageNotFoundException;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.model.entity.Image;
import com.example.demo.repository.ImageRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.request.AddImageRequest;
import com.example.demo.request.UpdateImageRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;

    @Override
    public Image addImage(AddImageRequest request) {
        return Optional.of(request)
                .filter(req -> !imageRepository.existsByTitle(req.getTitle())) // Check if the title already exists
                .map(this::createImage) // Create the Image object
                .map(imageRepository::save) // Save the created image
                .orElseThrow(() -> new ImageAlreadyExistsException("Image Already Exists")); // Throw exception if the image exists
    }

    private Image createImage(AddImageRequest request) {

        return productRepository.findById(request.getProduct().getId())
                .map(product -> new Image(
                        product,
                        request.getUrl(),
                        request.getImage(),
                        request.getTitle()

                ))
                .orElseThrow( ()-> new ProductNotFoundException("Product Not Found"));

    }

    @Override
    public Image getImage(Long id) {
        return imageRepository.findById(id).orElseThrow(
                ()-> new ImageNotFoundException("Image Not Found")
        );
    }

    @Override
    public Image updateImage(UpdateImageRequest request, Long id) {
        return imageRepository.findById(id)
                .map(existingImage -> updateExistngImage(request,existingImage))
                .map(imageRepository::save)
                .orElseThrow(()-> new ImageNotFoundException("Image Not Found") // Throw exception if not found
        );

    }

    private Image updateExistngImage(UpdateImageRequest request, Image image) {
       image.setTitle(request.getTitle());
       image.setUrl(request.getUrl());
       image.setImage(request.getImage());
       image.setProduct(request.getProduct());
       return image;
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
