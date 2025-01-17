package com.example.demo.serivce.image;

import com.example.demo.model.entity.Image;
import com.example.demo.request.AddImageRequest;
import com.example.demo.request.UpdateImageRequest;

import java.util.List;

public interface IImageService {

    Image addImage(AddImageRequest image);
    Image getImage(Long id);
    Image updateImage(UpdateImageRequest image, Long id);
    void deleteImage(Long id);
    Image getImageByUrl(String url);
    List<Image> getAllImages();
    List<Image> getImagesByProduct(String product);
    Long getImageCountByProduct(String product);

}
