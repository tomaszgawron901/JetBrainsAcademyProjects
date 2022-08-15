package recipes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.contract.request.UserCredentialsRequest;
import recipes.mapping.ContractToDomainMapping;
import recipes.service.interfaces.IAuthService;

@RestController
@RequestMapping("api")
public class AuthController extends BaseController {
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserCredentialsRequest credentials) {
        boolean successfulRegistration = authService.register(ContractToDomainMapping.mapToDomain(credentials));
        if (successfulRegistration) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }

}
