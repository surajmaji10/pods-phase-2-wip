package pods.project.marketplaceservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pods.project.marketplaceservice.entities.Product;
import pods.project.marketplaceservice.repositories.ProductsRepository;
import pods.project.marketplaceservice.services.ProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class ProductController {


    private final ProductsRepository productsRepository;
    private RestTemplate restTemplate;
    private ProductService productService;

    @Autowired
    public ProductController(ProductsRepository productsRepository, ProductService productService) {
        this.productsRepository = productsRepository;
        this.restTemplate = new RestTemplate();
        this.productService = productService;
    }

    @GetMapping("products")
    public ResponseEntity<List<Product>> getAllProducts(){
            return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id){
        return productService.getProductById(id);
    }

    @PutMapping("/products")
    public ResponseEntity<?> updateProducts(@RequestBody Map<String, Object> request){
        return productService.updateProducts(request);
    }

}
