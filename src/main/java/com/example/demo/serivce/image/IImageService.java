package com.example.demo.serivce.image;

import com.example.demo.model.dto.ImageDto;
import com.example.demo.model.entity.Image;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;

public interface IImageService {

    List<ImageDto> addImage(List<MultipartFile> image, Long ProductId);
    ImageDto getImage(Long id);
    void updateImage(List<MultipartFile> image, Long id);
    void deleteImage(Long id);
    ImageDto getImageByUrl(String url);
    List<ImageDto> getAllImages();
    List<ImageDto> getImagesByProduct(String product);
    Long getImageCountByProduct(String product);

}
