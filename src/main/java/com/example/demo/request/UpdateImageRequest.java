package com.example.demo.request;


import com.example.demo.model.entity.Product;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Blob;

@Data
@AllArgsConstructor
public class UpdateImageRequest {
    private String url;
    @Lob
    private Blob image;
    private String name;
    private Product product;

}

