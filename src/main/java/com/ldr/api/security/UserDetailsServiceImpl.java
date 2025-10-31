package com.ldr.api.security;

import com.ldr.api.model.User;
import com.ldr.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.findByUsername(username);

            // Check if user is active
            if (!user.isActive()) {
                throw new UsernameNotFoundException("User is not active: " + username);
            }

            // Map user roles to authorities
            Collection<? extends GrantedAuthority> authorities = mapRolesToAuthorities(user.getRole());

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .accountExpired(false)
                    .accountLocked(user.getLockedUntil() != null && user.getLockedUntil().isAfter(java.time.LocalDateTime.now()))
                    .credentialsExpired(false)
                    .disabled(!user.isActive())
                    .build();

        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found with username: " + username, e);
        }
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(String role) {
        if (role == null || role.trim().isEmpty()) {
            return Collections.emptyList();
        }

        // Convert role to Spring Security authority format (ROLE_ROLE_NAME)
        String authority = "ROLE_" + role.toUpperCase();
        return Collections.singletonList(new SimpleGrantedAuthority(authority));
    }
}