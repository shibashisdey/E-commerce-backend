package com.ecommerce.demo.service;


import com.ecommerce.demo.dto.product.ProductCreateRequest;
import com.ecommerce.demo.dto.product.ProductResponse;
import com.ecommerce.demo.exception.ResourceNotFoundException;
import com.ecommerce.demo.model.Product;
import com.ecommerce.demo.model.Seller;
import com.ecommerce.demo.repository.ProductRepository;
import com.ecommerce.demo.repository.SellerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    @Transactional
    public ProductResponse addProduct(ProductCreateRequest request) {
        Seller seller = sellerRepository.findById(request.getSellerId())
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with ID: " + request.getSellerId()));

        Product product = Product.builder()
                .pname(request.getPname())
                .price(request.getPrice())
                .discountPrice(request.getDiscountPrice())
                .pdescription(request.getPdescription())
                .pimage(request.getPimage())
                .pQuantity(request.getPQuantity())
                .category(request.getCategory())
                .brand(request.getBrand())
                .sku(request.getSku())
                .seller(seller)
                .build();

        productRepository.save(product);

        return mapToResponse(product);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));
        return mapToResponse(product);
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.findByPnameContainingIgnoreCase(keyword).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> getProductsBySeller(String sellerId) {
        return productRepository.findBySellerSellerId(sellerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setProductId(product.getProductId());
        response.setPname(product.getPname());
        response.setPrice(product.getPrice());
        response.setDiscountPrice(product.getDiscountPrice());
        response.setPdescription(product.getPdescription());
        response.setPimage(product.getPimage());
        response.setPQuantity(product.getPQuantity());
        response.setCategory(product.getCategory());
        response.setBrand(product.getBrand());
        response.setSku(product.getSku());
        response.setSellerId(product.getSeller().getSellerId());
        response.setSellerName(product.getSeller().getSellerName());
        return response;
    }
}
