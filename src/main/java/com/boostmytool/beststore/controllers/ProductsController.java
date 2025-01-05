package com.boostmytool.beststore.controllers;

import com.boostmytool.beststore.Product;
import com.boostmytool.beststore.models.ProductDto;
import com.boostmytool.beststore.services.ProductsRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsRepository repository;

    @GetMapping({"", "/"})
    public String showProductsList(Model model) {
        List<Product> products = repository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("products", products);
        return "products/index";

    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "products/CreateProduct";
    }

    @PostMapping("/create")
    public String createProduct(
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result) {

        if (productDto.getImagenFile().isEmpty()) {
            result.addError((new FieldError("productDto", "imagenFile", "The image file is required")));
        }

        if (result.hasErrors()) {
            return "products/CreateProduct";
        }


        //save image file
        MultipartFile image = productDto.getImagenFile();
        Date createDat = new Date();
        String storageFileName = createDat.getTime() + " _ " + image.getOriginalFilename();

        try {
            String uploadDir = "public/imagenes";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, Paths.get(uploadDir + "/" + storageFileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }

        Product product = new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setPrice(productDto.getPrice());
        product.setCategory(productDto.getCategory());
        product.setDescription(productDto.getDescription());
        product.setCreateDat(createDat);
        product.setImagenFile(storageFileName);

        repository.save(product);

        return "redirect:/products";

    }

    @GetMapping("/edit")
    public String showEditPage(
            Model model,
            @RequestParam int id) {

        try {
            Product product = repository.findById(id).get();
            model.addAttribute("product", product);

            ProductDto productDto = new ProductDto();
            productDto.setName(product.getName());
            productDto.setBrand(product.getBrand());
            productDto.setPrice(product.getPrice());
            productDto.setCategory(product.getCategory());
            productDto.setDescription(product.getDescription());
            productDto.setCreateDat(product.getCreateDat()); // Asigna createDat

            model.addAttribute("productDto", productDto);

        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return "redirect:/products";
        }

        return "products/editProduct";
    }

    @PostMapping("/edit")
    public String updateProduct(
            Model model,
            @RequestParam int id,
            @Valid @ModelAttribute ProductDto productDto,
            BindingResult result) {

        try {
            Product product = repository.findById(id).get();
            model.addAttribute("product", product);

            if (result.hasErrors()) {
                return "products/editProduct";
            }
            if (!productDto.getImagenFile().isEmpty()) {
                //delete old image
                String uploadDir = "public/imagenes";
                Path oldImagePath = Paths.get(uploadDir + product.getImagenFile());

                try {
                    Files.delete(oldImagePath);
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }


                MultipartFile image = productDto.getImagenFile();
                Date createDat = new Date();
                String storageFileName = createDat.getTime() + " _ " + image.getOriginalFilename();

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, Paths.get(uploadDir + "/" + storageFileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
                product.setImagenFile(storageFileName);
            }
            product.setName(productDto.getName());
            product.setBrand(productDto.getBrand());
            product.setCategory(productDto.getCategory());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());

            repository.save(product);

        } catch (Exception ex) {
            System.out.println("Exeption: " + ex.getMessage());
        }

        return "redirect:/products";
    }

    @GetMapping("/delete")
    public String deleteproduct(
            @RequestParam int id) {
        try {
            Product product = repository.findById(id).get();
            //delete product image
            Path imagePath = Paths.get("public/imagenes/" + product.getImagenFile());
            try {

                Files.delete(imagePath);

            } catch (Exception ex) {
                System.out.println("Exception: " + ex.getMessage());
            }
             //delete product
            repository.delete(product);

        } catch (Exception ex) {
            System.out.println("Exception:" + ex.getMessage());
        }

        return "redirect:/products";

    }
    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> products = repository.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCaseOrCategoryContainingIgnoreCase(query, query, query);

        if (products.isEmpty()) {
            // Si no se encontraron productos, buscar por coincidencias más amplias
            products = repository.findAll();
            model.addAttribute("noResults", true);
        } else {
            model.addAttribute("noResults", false);
        }

        model.addAttribute("products", products);
        model.addAttribute("query", query);
        return "/products/search";
    }



}


