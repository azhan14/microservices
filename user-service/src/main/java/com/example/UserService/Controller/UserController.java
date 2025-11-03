package com.example.UserService.Controller;

import com.example.UserService.Model.ProductDTO;
import com.example.UserService.Model.User;
import com.example.UserService.Model.UserProductResponse;
import com.example.UserService.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping
    public List<User> users() {
        return userService.getUsers();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User created = userService.createUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody User user) {

        User updated = userService.updateUser(id, user);
        return ResponseEntity.ok(updated);

    }

    @GetMapping("/{userId}/product/{productId}")
    public UserProductResponse getUserWithProduct(@PathVariable Long userId,
                                                  @PathVariable Long productId) {
        User user = userService.getUser(userId);
        ProductDTO product = userService.getProductForUser(productId);
        return new UserProductResponse(user,product);
    }
}
