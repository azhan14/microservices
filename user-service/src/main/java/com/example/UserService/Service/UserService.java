package com.example.UserService.Service;

import com.example.UserService.Exception.ResourceNotFoundException;
import com.example.UserService.Model.ProductDTO;
import com.example.UserService.Model.User;
import com.example.UserService.Repository.UserRepository;
import com.example.UserService.client.ProductClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProductClient productClient;

    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser.isPresent()) {
            throw new ResourceNotFoundException("Email already exists.");
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user not found."));

        if(updatedUser.getName() != null) user.setName(updatedUser.getName());
        if(updatedUser.getEmail() != null) user.setEmail(updatedUser.getEmail());

        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackGetProduct")
    @Retry(name = "productService")
    public ProductDTO getProductForUser(Long productId) {
        return productClient.getProductById(productId);
    }

    public ProductDTO fallbackGetProduct(Long productId, Throwable throwable) {
        return new ProductDTO(productId, "Default Product","Default Description",0.0,0);
    }

}
