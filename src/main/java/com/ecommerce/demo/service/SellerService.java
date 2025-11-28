package com.ecommerce.demo.service;

import com.ecommerce.demo.dto.seller.SellerLoginRequest;
import com.ecommerce.demo.dto.seller.SellerRegisterRequest;
import com.ecommerce.demo.dto.seller.SellerResponse;
import com.ecommerce.demo.model.Seller;
import com.ecommerce.demo.repository.SellerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SellerResponse registerSeller(SellerRegisterRequest request) {
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
        Optional<Seller> sellerOpt = sellerRepository.findByUsername(request.getUsername());

        if (sellerOpt.isEmpty()) {
            throw new RuntimeException("Invalid username or password");
        }

        Seller seller = sellerOpt.get();

        if (!seller.getIsVerified()) {
            throw new RuntimeException("Seller account is not verified");
        }

        if (!passwordEncoder.matches(request.getPassword(), seller.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return mapToResponse(seller);
    }

    public SellerResponse getSellerById(String sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found with ID: " + sellerId));
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
