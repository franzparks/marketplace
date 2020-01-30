package francis.market.user.service;

import francis.market.user.entity.User;
import francis.market.user.exception.UserAlreadyExistException;
import francis.market.user.exception.UserNotFoundException;

import java.security.NoSuchAlgorithmException;

/**
 * UserService
 */

public interface UserService {

	void register(User user) throws UserAlreadyExistException, NoSuchAlgorithmException;

	User login(User user) throws UserNotFoundException;
}
