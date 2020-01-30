package francis.market.common.security.session;

import francis.market.user.entity.User;

/**
 * SessionUser
 */
public class SessionUser {

    private static final ThreadLocal<User> sessionUser = new ThreadLocal<User>() {
        @Override
        protected User initialValue() {
            return null;
        }
    };

    public static User getSessionUser() {
        return sessionUser.get();
    }

    public static void setSessionUser(User user) {
        sessionUser.set(user);
    }

}
