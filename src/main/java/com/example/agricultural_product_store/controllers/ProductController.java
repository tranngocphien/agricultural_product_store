package com.example.agricultural_product_store.controllers;

import com.example.agricultural_product_store.config.exception.ResourceNotFoundException;
import com.example.agricultural_product_store.dto.request.CommentRequest;
import com.example.agricultural_product_store.dto.request.CreateProductRequest;
import com.example.agricultural_product_store.dto.request.UpdateProductRequest;
import com.example.agricultural_product_store.dto.response.PaginationInfo;
import com.example.agricultural_product_store.dto.response.ProductResponse;
import com.example.agricultural_product_store.dto.response.ResponseData;
import com.example.agricultural_product_store.models.entity.Comment;
import com.example.agricultural_product_store.models.entity.Product;
import com.example.agricultural_product_store.models.entity.User;
import com.example.agricultural_product_store.services.ProductServiceImpl;
import com.example.agricultural_product_store.services.UserServiceImpl;
import com.example.agricultural_product_store.services.template.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductServiceImpl productService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public ProductController(ProductServiceImpl productService, ModelMapper modelMapper, UserService userService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping()
    @CrossOrigin
    public ResponseData<List<ProductResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage = productService.listPage(pageable);
        List<ProductResponse> products = productPage.getContent().stream().map(product -> {
            return modelMapper.map(product, ProductResponse.class);
        }).collect(Collectors.toList());
        PaginationInfo paginationInfo = new PaginationInfo();
        paginationInfo.setPage(productPage.getNumber());
        paginationInfo.setSize(products.size());
        paginationInfo.setTotalPage(productPage.getTotalPages());
        paginationInfo.setTotalElement(productPage.getTotalElements());
        return ResponseData.onSuccess(products, paginationInfo);
    }

    @GetMapping("/search")
    public ResponseData<List<ProductResponse>> search(@RequestParam String keyword) {
        List<Product> products = productService.searchProduct(keyword);
        List<ProductResponse> responses = products.stream().map(product -> {
            return modelMapper.map(product, ProductResponse.class);
        }).collect(Collectors.toList());
        return ResponseData.onSuccess(responses);
    }

    @GetMapping("/filter")
    public ResponseData<List<ProductResponse>> getListProductsByCategoryId(@RequestParam(name = "categoryId") Long id) {
        List<Product> products = productService.findProductByCategoryId(id);
        List<ProductResponse> responses = products.stream().map(
                product -> modelMapper.map(product, ProductResponse.class)
        ).collect(Collectors.toList());
        return ResponseData.onSuccess(responses);
    }

    @GetMapping("/bestSeller")
    public ResponseData<List<ProductResponse>> getBestSeller() {
        List<Product> products = productService.bestSellerProduct();
        List<ProductResponse> responses = products.stream().map(
                product -> modelMapper.map(product, ProductResponse.class)
        ).collect(Collectors.toList());
        return ResponseData.onSuccess(responses);
    }

    @GetMapping("/{id}")
    public ResponseData<ProductResponse> detail(@PathVariable("id") Long id) {
        return ResponseData.onSuccess(modelMapper.map(productService.detail(id).orElseThrow(() -> new ResourceNotFoundException("Product not found")), ProductResponse.class));
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<ProductResponse> createProduct(@RequestBody CreateProductRequest request) {
        return ResponseData.onSuccess(modelMapper.map(productService.createProduct(request), ProductResponse.class));
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData<ProductResponse> updateProduct(@RequestBody UpdateProductRequest request) {
        return ResponseData.onSuccess(modelMapper.map(productService.updateProduct(request), ProductResponse.class));
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseData delete(@PathVariable("id") Long id) {
        return productService.deleteProduct(id);
    }

    @PostMapping("/comment/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseData<Comment> comment(Authentication authentication, @PathVariable("id") Long id, @RequestBody CommentRequest comment) {
        return ResponseData.onSuccess(productService.commentProduct(authentication, id, comment));
    }

    @PostMapping("/favourite/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseData<List<ProductResponse>> favourite(@PathVariable("id") Long id, Authentication authentication) {
        User user = userService.getUserByUsername(authentication.getName()).orElseThrow(
                () -> new ResourceNotFoundException("User not found")
        );
        List<Product> products = productService.favouriteProduct(user, id);
        List<ProductResponse> responses = products.stream().map(
                product -> modelMapper.map(product, ProductResponse.class)
        ).collect(Collectors.toList());
        return ResponseData.onSuccess(responses);
    }
}
