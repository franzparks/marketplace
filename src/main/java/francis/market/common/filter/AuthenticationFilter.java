package francis.market.common.filter;

import francis.market.common.security.session.SessionUser;
import francis.market.user.entity.User;
import francis.market.user.exception.UserNotFoundException;
import francis.market.user.service.UserService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * Authentication filter class.
 * It looks for the request header 'Authorization' and validates the user against db.
 */
@Slf4j
@Getter
public class AuthenticationFilter implements Filter {

    @Autowired
    private UserService userService;

    private static final List<String> AUTH_SKIP_URLS = new ArrayList<String>() {
        {
            add("/user");
            add("/user/login");
            add("/h2-console");
        }
    };

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (!AUTH_SKIP_URLS.contains(request.getPathInfo())) {
            String authHeader = request.getHeader("Authorization");
            if (!StringUtils.isEmpty(authHeader) && authHeader.startsWith("Basic ")) {
                authHeader = authHeader.substring(authHeader.lastIndexOf(" ") + 1, authHeader.length());
                String decodeAuthHeader = new String(Base64.getDecoder().decode(authHeader), StandardCharsets.UTF_8);
                log.info("Decoded Auth Header {}", decodeAuthHeader);
                String[] tokens = decodeAuthHeader.split(":");
                if (tokens.length > 1) {
                    User user = new User();
                    user.setEmail(tokens[0]);
                    user.setPassword(tokens[1]);
                    try {
                        user = getUserService().login(user);
                        SessionUser.setSessionUser(user);
                    } catch (UserNotFoundException ex) {
                        SessionUser.setSessionUser(null);
                        log.error("Authentication failed.", ex);
                    }
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
