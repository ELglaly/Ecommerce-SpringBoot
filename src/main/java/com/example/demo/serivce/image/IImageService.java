package com.example.demo.serivce.image;

import com.example.demo.model.entity.Image;

import java.util.List;

public interface IImageService {

    Image addImage(Image image);
    Image getImage(Long id);
    Image updateImage(Image image);
    void deleteImage(Long id);
    Image getImageByUrl(String url);
    List<Image> getAllImages();
    List<Image> getImagesByProduct(String product);
    Long getImageCountByProduct(String product);

}
