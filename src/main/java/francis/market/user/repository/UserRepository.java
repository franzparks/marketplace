package francis.market.user.repository;

import java.util.Optional;

import francis.market.user.entity.User;

/**
 * JPA repository class, to perform  all User related CRUD operations and lookup.
 */

public interface UserRepository {

    User register(User user);

    User login(String userId, String password);

    boolean isUserAlreadyExist(String userId);
    
    Optional<User> getUserByEmail(String email);
}
