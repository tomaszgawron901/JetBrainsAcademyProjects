package recipes.service.implementations;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import recipes.domain.model.AppUserDetails;
import recipes.repository.IUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final IUserRepository userRepository;

    public UserDetailsServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userDto = userRepository.findById(username);
        if (userDto.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new AppUserDetails(userDto.get().getEmail(), userDto.get().getPassword());
    }
}
