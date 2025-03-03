package pods.project.marketplaceservice.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pods.project.marketplaceservice.entities.Product;
import pods.project.marketplaceservice.repositories.ProductsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Service
public class ProductService {
    private final ProductsRepository productsRepository;
    private RestTemplate restTemplate;

    @Value("${host.url}")
    private String localhost;
    @Value("${account.service.url}")
    private String accountServiceUrl;
    @Value("${wallet.service.url}")
    private String walletServiceUrl;
    @Value("${marketplace.service.url}")
    private String marketplaceServiceUrl;

    @Autowired
    public ProductService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
        this.restTemplate = new RestTemplate();
    }

    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = new ArrayList<>();
        products = productsRepository.findAll();
        return  ResponseEntity.ok().body(products);
    }

    public ResponseEntity<?> getProductById(Integer id) {
        List<Product> products = productsRepository.findProductByIdIs(id);
        if(products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(productNotFound(id));
        }
        return  ResponseEntity.ok().body(products.get(0));
    }

    public ResponseEntity<?> updateProducts(Map<String, Object> request) {
        String order_id = request.get("order_id").toString();
        List<Map<String, Integer>> products = (List<Map<String, Integer>>) request.get("products");

        for(Map<String, Integer> product: products){
            Integer id = product.get("product_id");
            Integer quantity = product.get("quantity");
            productsRepository.updateQuantity(id, quantity);
        }

        return ResponseEntity.ok().body(productsUpdated(order_id));
    }

    private String productsUpdated(String orderId) {
        return "Products updated for order with id=" + orderId;
    }

    private static String productNotFound(Integer id) {
        return  "Product with id=" + id + " not found";
    }


}
