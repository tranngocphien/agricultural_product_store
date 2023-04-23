package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.config.security.JwtUtil;
import com.example.agricultural_product_store.dto.request.LoginRequest;
import com.example.agricultural_product_store.dto.request.RegisterRequest;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.dto.response.JwtResponse;
import com.example.agricultural_product_store.models.entity.ERole;
import com.example.agricultural_product_store.models.entity.Role;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.repositories.RoleRepository;
import com.example.agricultural_product_store.repositories.UserRepository;
import com.example.agricultural_product_store.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;
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

    private final UserService accountService;

    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserService accountService) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountService = accountService;
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
    public ResponseData<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        if(userRepository.existsByUsername(registerRequest.getUsername())){
            return ResponseData.onFail(HttpStatus.BAD_REQUEST.value(), "Username is already taken");
        }

        Role role = roleRepository.findByName(ERole.ROLE_USER);
        Set<Role> roles = new HashSet<Role>();
        roles.add(role);
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setRoles(roles);
        user.setCreateTime(Timestamp.valueOf(LocalDateTime.now()));
        user.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(user);

        return ResponseData.onSuccess("SUCCESS");
    }

}
