package cinema.service;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    private final String password = "super_secret";

    public boolean isValidPassword(String password) {
        return this.password.equals(password);
    }
}
