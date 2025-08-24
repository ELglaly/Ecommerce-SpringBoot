package com.example.ecommerce.entity;

import java.sql.Blob;

import com.example.ecommerce.enums.StorageType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;


@Entity
@Table(name="Images")
@NoArgsConstructor 
@AllArgsConstructor
@Setter
@Getter
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Name contains invalid characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 512, message = "URL cannot exceed 512 characters")
    @Pattern(regexp = "^https?://.*", message = "Invalid URL format")
    @NotBlank
    @Column(length = 512)
    private String url;

    @NotBlank(message = "File Path is mandatory")
    @Column(name = "file_path", length = 512)
    private String filePath;

    @Column(nullable = false)
    @NotNull(message = "Storage Type is Required")
    @Enumerated(EnumType.STRING)
    private StorageType storageType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;


}
