package com.example.demo.serivce.image;

import com.example.demo.model.dto.ImageDto;
import com.example.demo.model.entity.Image;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;

public interface IImageService extends IImageManagementService,IImageSearchService {
}
