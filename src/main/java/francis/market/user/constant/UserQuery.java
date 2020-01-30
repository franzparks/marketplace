package francis.market.user.constant;

/**
 * UserQuery
 */
public final class UserQuery {
    public static final String IS_USER_ID_ALREADY_EXIST = "SELECT count(ID) FROM USER WHERE email = :email";
    public static final String IS_AUTHENTICATED = "from User where email = :email and password = :password";
    public static final String USER_BY_EMAIL = "from User WHERE email = :email";
}
