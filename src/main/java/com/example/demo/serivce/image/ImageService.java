package com.example.demo.serivce.image;

import com.example.demo.exceptions.ImageNotFoundException;
import com.example.demo.model.entity.Image;
import com.example.demo.repository.ImageRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;

    @Override
    public Image addImage(Image image) {
        return null;
    }

    @Override
    public Image getImage(Long id) {
        return null;
    }

    @Override
    public Image updateImage(Image image) {
        return null;
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
