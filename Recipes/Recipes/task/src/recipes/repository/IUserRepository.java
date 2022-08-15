package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.contract.data.UserDto;

@Repository
public interface IUserRepository extends CrudRepository<UserDto, String> {
}
