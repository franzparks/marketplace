package francis.market.user.resource;

import francis.market.common.exception.MarketException;
import francis.market.common.model.MarketResponse;
import francis.market.common.model.UserVO;
import francis.market.user.entity.User;
import francis.market.user.exception.UserAlreadyExistException;
import francis.market.user.exception.UserNotFoundException;
import francis.market.user.service.UserService;
import francis.market.user.util.ObjectMapperUtil;
import francis.market.user.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * User REST resource class.
 * Is responsible for create/update/delete/get User information.
 */

@Path(value = "/users")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class UserResource {

    @Autowired
    private UserService userService;

    /**
     * Register new User.
     *
     * @return Response.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(@Valid UserVO userVO) throws MarketException {
        try {
            User userEntity = ObjectMapperUtil.userEntity(userVO);
            log.info("Registration processing for the user: {}", userEntity.getEmail());
            userService.register(userEntity);
        } catch (UserAlreadyExistException e) {
            log.error("User already exists: ", e);
            throw new MarketException(Response.Status.BAD_REQUEST.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new MarketException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "We are unable to process your request, please try again.");
        }
        return Response.status(Response.Status.CREATED).entity(
                MarketResponse.builder()
                        .withStatusCode(Response.Status.CREATED.getStatusCode())
                        .withMessage("User registration successful.").build()
        ).build();
    }

    /**
     * Authenticate user against the system.
     *
     * @return Response.
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(UserVO userVO) throws MarketException {
        User userEntity;
        if (StringUtils.isEmpty(userVO.getEmail()) || StringUtils.isEmpty(userVO.getPassword())) {
            throw new MarketException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "UserName & Password shouldn't be empty.");
        }
        try {
            userEntity = ObjectMapperUtil.userEntity(userVO);
            userEntity.setPassword(PasswordUtil.hash(userEntity.getPassword()));
            userService.login(userEntity);
        } catch (UserNotFoundException e) {
            log.error("Exception: ", e);
            throw new MarketException(Response.Status.NOT_FOUND.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new MarketException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "We are unable to process your request, please try again.");
        }
        return Response.status(Response.Status.OK).entity(
                MarketResponse.builder()
                        .withStatusCode(Response.Status.OK.getStatusCode())
                        .withMessage("Logged in successfully.")
                        .withUserVO(ObjectMapperUtil.userVO(userEntity))
                        .build()
        ).build();
    }

    /**
     * Authenticate user against the system.
     *
     * @return Response.
     */
    @GET
    @Path("/sanity-check")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sanityCheck()  {
        
        return Response.status(Response.Status.OK).entity(
                MarketResponse.builder()
                        .withStatusCode(Response.Status.OK.getStatusCode())
                        .withMessage("Application start successful.")
                        .build()
        ).build();
    }
}
