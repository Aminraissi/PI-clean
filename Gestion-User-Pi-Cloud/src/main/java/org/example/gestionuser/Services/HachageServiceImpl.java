package org.example.gestionuser.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class HachageServiceImpl implements IHachageService {
    private final PasswordEncoder passwordEncoder;



    public String hashPassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public boolean verifyPassword(String rawPassword, String hashedPassword) {
        if (rawPassword == null || hashedPassword == null || hashedPassword.isBlank()) {
            return false;
        }
        try {
            return passwordEncoder.matches(rawPassword, hashedPassword);
        } catch (IllegalArgumentException ex) {
            log.warn("Invalid password hash format for login attempt: {}", ex.getMessage());
            return false;
        }
    }
}
