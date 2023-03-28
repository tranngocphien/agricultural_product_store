package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.config.security.JwtUtil;
import com.example.agricultural_product_store.dto.request.LoginRequest;
import com.example.agricultural_product_store.dto.request.RegisterRequest;
import com.example.agricultural_product_store.dto.response.JwtResponse;
import com.example.agricultural_product_store.models.entity.Role;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.RoleRepository;
import com.example.agricultural_product_store.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(
                jwt, userDetails.getUsername(), roles
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            return  ResponseEntity.badRequest().body("Username is already taken");
        }

        Role role = roleRepository.findByName("ROLE_USER");
//        User user = new User(1L,registerRequest.getUsername(),passwordEncoder.encode(registerRequest.getPassword()),registerRequest.getEmail(), Collections.singleton(role) );
//        userRepository.save(user);

        return ResponseEntity.ok("");
    }



    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello2")
    public String hello2() {
        return "hello";
    }
}
