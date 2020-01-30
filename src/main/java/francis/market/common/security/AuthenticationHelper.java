package francis.market.common.security;

import francis.market.common.exception.AuthenticationFailedException;
import francis.market.user.entity.User;

/**
 * AuthenticationHelper
 */
public final class AuthenticationHelper {

    private AuthenticationHelper() {

    }
    public static void isRequestAuthenticated(User user) throws AuthenticationFailedException {
        if (user == null) {
            throw new AuthenticationFailedException("Authentication Failed.");
        }
    }
}
