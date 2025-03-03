package pods.project.marketplaceservice.controllers;

import jakarta.persistence.LockModeType;
import jakarta.transaction.Transactional;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pods.project.marketplaceservice.entities.Order;
import pods.project.marketplaceservice.entities.OrderItem;
import pods.project.marketplaceservice.entities.Product;
import pods.project.marketplaceservice.repositories.OrderRepository;
import pods.project.marketplaceservice.repositories.ProductsRepository;
import pods.project.marketplaceservice.services.OrderService;

import java.util.*;

@RestController
public class OrderController {


    private final OrderRepository orderRepository;
    private final ProductsRepository productsRepository;
    private RestTemplate restTemplate;
    private OrderService orderService;

    @Autowired
    public OrderController(OrderRepository orderRepository, ProductsRepository productsRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.restTemplate = new RestTemplate();
        this.productsRepository = productsRepository;
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Product>> getAllOrders(){
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/users/{id}")
    public ResponseEntity<List<Map<String, Object>>> getAllOrdersByUserId(@PathVariable Integer id){
        return orderService.getAllOrdersByUserId(id);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Integer id){
        return orderService.deleteOrder(id);
    }



    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id){
        return orderService.getProductById(id);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable Integer id, @RequestBody Map<String, Object> request){
        return orderService.updateProductById(id, request);
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody Map<String, Object> request){
        return orderService.createOrder(request);
    }


    @DeleteMapping("/marketplace")
    public ResponseEntity<?> deleteAllPlaced(){
        return orderService.deleteAllPlaced();
    }

    @DeleteMapping("/marketplace/users/{id}")
    public ResponseEntity<?> deleteAllPlacedForUser(@PathVariable Integer id){
        return orderService.deleteAllPlacedForUser(id);
    }

}
