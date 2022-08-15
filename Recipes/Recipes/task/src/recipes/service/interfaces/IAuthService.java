package recipes.service.interfaces;

import recipes.domain.model.AppUserDetails;

public interface IAuthService {
    boolean register(AppUserDetails user);
}