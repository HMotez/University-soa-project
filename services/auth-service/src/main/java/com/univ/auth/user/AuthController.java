package com.univ.auth.auth;

import com.univ.auth.security.JwtService;
import com.univ.auth.user.User;
import com.univ.auth.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtService;

    // Modèle de requête pour l'enregistrement (pour la clarté)
    record RegisterRequest(String username, String password, String email, String role) {}

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }

        User newUser = new User();
        newUser.setUsername(request.username());
        newUser.setEmail(request.email());
        newUser.setRole(request.role() != null ? request.role() : "STUDENT");
        newUser.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(newUser);

        // Générer un token pour l'utilisateur fraîchement créé
        String token = jwtService.generateToken(newUser.getUsername(), newUser.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("accessToken", token);
        return ResponseEntity.ok(response);
    }

    // Modèle de requête pour la connexion
    record SignInRequest(String username, String password) {}

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody SignInRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(request.password(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid Credentials");
        }

        // Connexion réussie : Génération du JWT
        String token = jwtService.generateToken(user.getUsername(), user.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("accessToken", token);
        response.put("role", user.getRole());
        response.put("type", "Bearer");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/me")
public ResponseEntity<?> getCurrentUser(@RequestHeader("Authorization") String authHeader) {

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(401).body("Missing or invalid token");
    }

    String token = authHeader.substring(7);
    String username = jwtService.extractUsername(token);

    User user = userRepository.findByUsername(username).orElse(null);

    if (user == null) {
        return ResponseEntity.status(404).body("User not found");
    }

    Map<String, Object> response = new HashMap<>();
    response.put("id", user.getId());
    response.put("username", user.getUsername());
    response.put("email", user.getEmail());
    response.put("role", user.getRole());

    return ResponseEntity.ok(response);
}

}