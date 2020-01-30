package francis.market.user.repository.impl;

import francis.market.user.constant.UserQuery;
import francis.market.user.entity.User;
import francis.market.user.repository.UserRepository;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Optional;

/**
 * UserRepositoryImpl
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User register(User user) {
        Serializable userId = sessionFactory.getCurrentSession().save(user);
        return user;
    }

    @Override
    public User login(String userid, String password) {
        Query query = sessionFactory.getCurrentSession().createQuery(UserQuery.IS_AUTHENTICATED);
        query.setString("email", userid);
        query.setString("password", password);
        User userFetched = (User) query.uniqueResult();
        return userFetched;
    }

    @Override
    public boolean isUserAlreadyExist(String userId) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(UserQuery.IS_USER_ID_ALREADY_EXIST);
        query.setString("email", userId);
        BigInteger count = (BigInteger) query.uniqueResult();
        if (count.intValue() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        Query query = sessionFactory.getCurrentSession().createQuery(UserQuery.USER_BY_EMAIL);
        query.setString("email", email);
        User user = (User) query.uniqueResult();
        return Optional.ofNullable(user);
    }
}
