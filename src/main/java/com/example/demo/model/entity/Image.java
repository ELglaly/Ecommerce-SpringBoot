package com.example.demo.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;


@Entity
@Table(name="Images")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @Lob
    private Blob image;
    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn()
    private Product product;


    public Image(Builder builder) {
        this.id = builder.id;
        this.url = builder.url;
        this.image = builder.image;
        this.name = builder.name;
        this.product = builder.product;
    }

    public static class Builder {
        private Long id;
        private String url;
        private Blob image;
        private String name;
        private Product product;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        public Builder url(String url) {
            this.url = url;
            return this;
        }
        public Builder image(Blob image) {
            this.image = image;
            return this;
        }
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        public Builder product(Product product) {
            this.product = product;
            return this;
        }
        public Image build() {
            return new Image(this);
        }
    }

}
