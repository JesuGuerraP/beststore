package com.boostmytool.beststore.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class ProductDto {

    @NotEmpty(message = "The name is required")
    private String name;

    @NotEmpty(message = "The brand is required")
    private String brand;

    @NotEmpty(message = "The category is required")
    private String category;

    @Min(0)
    private double price;



    @Size(min = 10, message = "the description should be at last 10 characters")
    @Size(max = 2000, message = "the description cannot exceed 2000  characters")
    private String description;

    private MultipartFile imagenFile;

    private Date createDat;

    public @NotEmpty(message = "The name is required") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "The name is required") String name) {
        this.name = name;
    }

    public @NotEmpty(message = "The brand is required") String getBrand() {
        return brand;
    }

    public void setBrand(@NotEmpty(message = "The brand is required") String brand) {
        this.brand = brand;
    }

    public @NotEmpty(message = "The category is required") String getCategory() {
        return category;
    }

    public void setCategory(@NotEmpty(message = "The category is required") String category) {
        this.category = category;
    }

    @Min(0)
    public double getPrice() {
        return price;
    }

    public void setPrice(@Min(0) double price) {
        this.price = price;
    }

    public @Size(min = 10, message = "the description should be at last 10 characters") @Size(max = 2000, message = "the description cannot exceed 2000  characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(min = 10, message = "the description should be at last 10 characters") @Size(max = 2000, message = "the description cannot exceed 2000  characters") String description) {
        this.description = description;
    }

    public MultipartFile getImagenFile() {
        return imagenFile;
    }

    public void setImagenFile(MultipartFile imagenFile) {
        this.imagenFile = imagenFile;
    }
    public Date getCreateDat() {
        return createDat;
    }

    // Setter para createDat con lógica de validación
    public void setCreateDat(Date createDat) {
        if (createDat == null) {
            throw new IllegalArgumentException("La fecha de creación no puede ser nula");
        }
        if (createDat.after(new Date())) {
            throw new IllegalArgumentException("La fecha de creación no puede ser una fecha futura");
        }
        this.createDat = createDat;
    }

}
