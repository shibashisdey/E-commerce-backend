package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.seller.SellerLoginRequest;
import com.ecommerce.demo.dto.seller.SellerRegisterRequest;
import com.ecommerce.demo.dto.seller.SellerResponse;
import com.ecommerce.demo.exception.AuthenticationException;
import com.ecommerce.demo.exception.DuplicateResourceException;
import com.ecommerce.demo.exception.ResourceNotFoundException;
import com.ecommerce.demo.model.Seller;
import com.ecommerce.demo.repository.SellerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SellerResponse registerSeller(SellerRegisterRequest request) {
        if (sellerRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateResourceException("Username '" + request.getUsername() + "' is already taken.");
        }
        if (sellerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email '" + request.getEmail() + "' is already registered.");
        }

        Seller seller = Seller.builder()
                .sellerName(request.getSellerName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .companyName(request.getCompanyName())
                .sellerAddress(request.getSellerAddress())
                .email(request.getEmail())
                .Sphone(request.getSphone())
                .gstNumber(request.getGstNumber())
                .isVerified(false) // Sellers might need a verification process
                .build();

        sellerRepository.save(seller);

        return mapToResponse(seller);
    }

    public SellerResponse loginSeller(SellerLoginRequest request) {
        Seller seller = sellerRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthenticationException("Invalid username or password."));

        if (!passwordEncoder.matches(request.getPassword(), seller.getPassword())) {
            throw new AuthenticationException("Invalid username or password.");
        }
        
        if (seller.getIsVerified() == null || !seller.getIsVerified()) {
            throw new AuthenticationException("Seller account is not verified.");
        }

        return mapToResponse(seller);
    }

    public SellerResponse getSellerById(String sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found with ID: " + sellerId));
        return mapToResponse(seller);
    }

    private SellerResponse mapToResponse(Seller seller) {
        SellerResponse response = new SellerResponse();
        response.setSellerId(seller.getSellerId());
        response.setSellerName(seller.getSellerName());
        response.setUsername(seller.getUsername());
        response.setCompanyName(seller.getCompanyName());
        response.setSellerAddress(seller.getSellerAddress());
        response.setEmail(seller.getEmail());
        response.setSphone(seller.getSphone());
        response.setIsVerified(seller.getIsVerified());
        response.setRating(seller.getRating());
        return response;
    }
}
