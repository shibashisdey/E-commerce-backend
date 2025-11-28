package com.ecommerce.demo.security;

import com.ecommerce.demo.model.Seller;
import com.ecommerce.demo.model.User;
import com.ecommerce.demo.repository.SellerRepository;
import com.ecommerce.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Try to find as User first
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
            );
        }

        // Try to find as Seller
        Optional<Seller> sellerOpt = sellerRepository.findByUsername(username);
        if (sellerOpt.isPresent()) {
            Seller seller = sellerOpt.get();
            return new org.springframework.security.core.userdetails.User(
                    seller.getUsername(),
                    seller.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_SELLER"))
            );
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}