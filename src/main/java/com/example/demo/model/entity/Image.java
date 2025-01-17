package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Images")
@Getter @Setter

public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String url;

    @Lob
    private Blob image;
    private String title;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    private Product product;

}
