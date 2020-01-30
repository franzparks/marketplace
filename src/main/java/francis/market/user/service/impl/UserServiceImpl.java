package francis.market.user.service.impl;

import francis.market.user.entity.User;
import francis.market.user.exception.UserAlreadyExistException;
import francis.market.user.exception.UserNotFoundException;
import francis.market.user.repository.UserRepository;
import francis.market.user.service.UserService;
import francis.market.user.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

/**
 * UserServiceImpl
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Register user.
     *
     * @param user
     * @throws UserAlreadyExistException
     * @throws NoSuchAlgorithmException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void register(User user) throws UserAlreadyExistException, NoSuchAlgorithmException {
        if (!userRepository.isUserAlreadyExist(user.getEmail())) {
            user.setPassword(PasswordUtil.hash(user.getPassword()));
            user = userRepository.register(user);
            log.info("User {} stored in db.", user.getEmail());
        } else {
            throw new UserAlreadyExistException("user id being used, please try with other.");
        }
    }

    /**
     * User login.
     *
     * @param user
     * @return
     * @throws UserNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public User login(User user) throws UserNotFoundException {
        String email = user.getEmail();
        log.info("Looking up for the user with email: {}, pwd: {}", email, user.getPassword());
        user = userRepository.login(user.getEmail(), user.getPassword());
        if (user == null) {
            throw new UserNotFoundException(String.format("User not found %s", email));
        }
        return user;
    }
}
