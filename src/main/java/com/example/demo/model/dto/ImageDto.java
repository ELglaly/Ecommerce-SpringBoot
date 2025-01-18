package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    private Long id;
    private String url;
    private Blob image;
    private String title;
    private ProductDto productDTO;
}
