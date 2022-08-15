package recipes.service.implementations;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.contract.data.UserDto;
import recipes.domain.model.AppUserDetails;
import recipes.repository.IUserRepository;
import recipes.service.interfaces.IAuthService;

import javax.validation.Validator;

@Service
public class AuthService extends BaseApplicationService implements IAuthService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(Validator validator, IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(validator);
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean register(AppUserDetails credentials) {
        ensureValid(credentials);

        if (userRepository.existsById(credentials.getEmail())) {
            return false;
        }

        var userDto = new UserDto();
        userDto.setEmail(credentials.getEmail());
        userDto.setPassword(passwordEncoder.encode(credentials.getPassword()));
        userRepository.save(userDto);

        return true;
    }

}
