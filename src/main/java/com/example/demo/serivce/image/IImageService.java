package com.example.demo.serivce.image;

import com.example.demo.model.entity.Image;
import com.example.demo.request.AddImageRequest;
import com.example.demo.request.UpdateImageRequest;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;

public interface IImageService {

    Image addImage(List<MultipartFile> image, Long ProductId);
    Image getImage(Long id);
    void updateImage(List<MultipartFile> image, Long id);
    void deleteImage(Long id);
    Image getImageByUrl(String url);
    List<Image> getAllImages();
    List<Image> getImagesByProduct(String product);
    Long getImageCountByProduct(String product);

}
