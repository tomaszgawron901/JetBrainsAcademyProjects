package cinema.service;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenGenerationService {
    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
