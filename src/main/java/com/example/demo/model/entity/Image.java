package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;


@Entity
@Table(name="Images")
@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @Lob
    private Blob image;
    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    private Product product;
}
