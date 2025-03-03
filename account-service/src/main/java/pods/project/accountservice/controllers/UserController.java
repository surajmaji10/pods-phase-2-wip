package pods.project.accountservice.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import pods.project.accountservice.entities.User;
import pods.project.accountservice.repositories.UserRepository;
import pods.project.accountservice.services.UserService;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    private static final Log log = LogFactory.getLog(UserController.class);
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private  UserService userService;


    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.restTemplate = new RestTemplate();
        this.userRepository = userRepository;
        this.userService = userService;

    }

    @GetMapping("users")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllusers();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findByUserId(@PathVariable Integer id) {
        return userService.findByUserId(id);
    }

    @PostMapping("/users")
    public ResponseEntity<?> insertIntoUsers(@RequestBody User user) {
        return userService.insertIntoUsers(user);
    }

    @PutMapping("/users")
    public ResponseEntity<?> updateUser(@RequestBody Map<String, Object> request) {
        return userService.updateUser(request);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteByUserId(@PathVariable Integer id) {
        return userService.deleteByUserId(id);
    }

    @DeleteMapping("/users")
    public ResponseEntity<?> deleteUsers() {
        return userService.deleteUsers();
    }

}
