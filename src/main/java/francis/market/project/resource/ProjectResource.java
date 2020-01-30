package francis.market.project.resource;

import francis.market.common.exception.AuthenticationFailedException;
import francis.market.common.exception.MarketException;
import francis.market.common.model.MarketResponse;
import francis.market.common.model.BidVO;
import francis.market.common.model.ProjectVO;
import francis.market.common.security.AuthenticationHelper;
import francis.market.common.security.session.SessionUser;
import francis.market.project.entity.Bid;
import francis.market.project.entity.Project;
import francis.market.project.exception.BidNotAllowedException;
import francis.market.project.exception.InvalidBidAmountException;
import francis.market.project.exception.ProjectNotFoundException;
import francis.market.project.service.ProjectService;
import francis.market.project.util.ObjectMapperUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Project resource class.
 */
@Path("/projects")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
public class ProjectResource {

    @Context
    private UriInfo uriInfo;

    @Autowired
    private ProjectService ProjectService;

    /**
     * Create brand new Project.
     * Request must have "Authorization" header with base64 encoded value (email:<hashed password>).
     *
     * @return Response.
     * @throws MarketException
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid ProjectVO ProjectVO) throws MarketException, URISyntaxException {
        Project ProjectEntity;
        try {
            AuthenticationHelper.isRequestAuthenticated(SessionUser.getSessionUser());
            ProjectEntity = ObjectMapperUtil.ProjectEntity(ProjectVO);
            ProjectEntity.setUser(SessionUser.getSessionUser());
            ProjectService.create(ProjectEntity);
        } catch (AuthenticationFailedException ae) {
            log.error("Exception: ", ae);
            throw new MarketException(Response.Status.UNAUTHORIZED.getStatusCode(),
                    "Authentication failed.");
        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new MarketException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Unable to process the request, please try again.");
        }
        return Response.created(new URI(uriInfo.getAbsolutePath() + "/" + ProjectEntity.getId())).entity(
                MarketResponse.builder().withStatusCode(Response.Status.CREATED.getStatusCode())
                        .withMessage(String.format("Project %s created successfuly.", ProjectEntity.getId()))
                        .withProjectVO(ProjectVO.builder().withId(ProjectEntity.getId())
                                .withProjectId(ProjectEntity.getProjectId()).build())
                        .build()
        ).build();
    }

    /**
     * Returns the requested Project details.
     * Request must have "Authorization" header with base64 encoded value (email:<hashed password>).
     *
     * @return Response.
     * @throws MarketException
     */
    @GET
    @Path("/{projectId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response detail(@PathParam("projectId") String projectId) throws MarketException {
        log.info("Processing Project projectDetails request for Id: {}", ProjectId);
        Project ProjectEntity;
        try {
            AuthenticationHelper.isRequestAuthenticated(SessionUser.getSessionUser());
            ProjectEntity = ProjectService.projectDetails(projectId);
        } catch (ProjectNotFoundException se) {
            log.error("Project not found", se);
            throw new MarketException(Response.Status.NOT_FOUND.getStatusCode(), se.getMessage());
        } catch (AuthenticationFailedException ae) {
            log.error("Authentication failed: ", ae);
            throw new MarketException(Response.Status.UNAUTHORIZED.getStatusCode(), "Authentication failed.");
        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new MarketException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Unable to process the request, please try again.");
        }
        return Response.ok().entity(
                MarketResponse.builder().withStatusCode(Response.Status.OK.getStatusCode())
                        .withProjectVO(ObjectMapperUtil.ProjectVO(ProjectEntity)).build()
        ).build();
    }

    /**
     * Return List of all Projects which are in active.
     * Request must have "Authorization" header with base64 encoded value (email:<hashed password>).
     *
     * @return Response
     * @throws MarketException
     */
    @GET
    @Path("/list")
    public Response list() throws MarketException {
        List<Project> Projects;
        try {
            AuthenticationHelper.isRequestAuthenticated(SessionUser.getSessionUser());
            Projects = ProjectService.list();
        } catch (ProjectNotFoundException se) {
            log.error("Project not found: ", se);
            throw new MarketException(Response.Status.NOT_FOUND.getStatusCode(), se.getMessage());
        } catch (AuthenticationFailedException ae) {
            log.error("Authentication failed: ", ae);
            throw new MarketException(Response.Status.UNAUTHORIZED.getStatusCode(), "Authentication failed.");
        } catch (Exception e) {
            log.error("Exception: ", e);
            throw new MarketException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Unable to process the request, please try again.");
        }
        return Response.ok().entity(
                MarketResponse.builder().withStatusCode(Response.Status.OK.getStatusCode())
                        .withProjectVO(Projects.stream().map(Project
                                -> ObjectMapperUtil.ProjectVO(Project)).collect(Collectors.toSet())).build()
        ).build();
    }

    /**
     * Bid for project.
     * Request must have "Authorization" header with base64 encoded value (email:<hashed password>).
     *
     * @return Response
     * @throws MarketException
     */
    @POST
    @Path("/{projectId}/bid")
    public Response bid(@PathParam("projectId") String projectId, @Valid BidVO bidVO) throws MarketException, URISyntaxException {
        Bid bidEntity;
        try {
            AuthenticationHelper.isRequestAuthenticated(SessionUser.getSessionUser());
            bidEntity = ObjectMapperUtil.bidEntity(bidVO);
            bidEntity.setUser(SessionUser.getSessionUser());
            bidEntity = ProjectService.bid(projectId, bidEntity);
        } catch (ProjectNotFoundException se) {
            log.error("Project not found: ", se);
            throw new MarketException(Response.Status.BAD_REQUEST.getStatusCode(), se.getMessage());
        } catch (BidNotAllowedException se) {
            log.error("Bid Not Allowed. ", se);
            throw new MarketException(Response.Status.BAD_REQUEST.getStatusCode(), se.getMessage());
        } catch (AuthenticationFailedException ae) {
            log.error("Authentication failed: ", ae);
            throw new MarketException(Response.Status.UNAUTHORIZED.getStatusCode(), "Authentication failed.");
        } catch (InvalidBidAmountException ie) {
            log.error("Not a valid bid amount: ", ie);
            throw new MarketException(Response.Status.BAD_REQUEST.getStatusCode(), ie.getMessage());
        } catch (Exception ex) {
            log.error("Exception: ", ex);
            throw new MarketException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Unable to process the request, please try again.");
        }
        return Response.created(new URI(
                uriInfo.getAbsolutePath() + "/project/" + bidEntity.getProject().getId() + "/bid/" + bidEntity.getId()))
                .entity(MarketResponse.builder().withStatusCode(Response.Status.CREATED.getStatusCode())
                        .withMessage("Bid posted successfully.")
                        .withBidVO(BidVO.builder().withId(bidEntity.getId()).build())
                        .build())
                .build();
    }

    /**
     * Retrieves the Latest Bid for the specified Project.
     * Request must have "Authorization" header with base64 encoded value (email:<hashed password>).
     *
     * @return Response.
     * @throws MarketException
     */
    @GET
    @Path("/{projectId}/bid")
    public Response latestBid(@PathParam("projectId") String projectId) throws MarketException {
        Bid bid;
        try {
            AuthenticationHelper.isRequestAuthenticated(SessionUser.getSessionUser());
            bid = ProjectService.getLatestBid(projectId);
        } catch (AuthenticationFailedException ae) {
            log.error("Authentication failed: ", ae);
            throw new MarketException(Response.Status.UNAUTHORIZED.getStatusCode(), "Authentication failed.");
        } catch (Exception ex) {
            log.error("Exception: ", ex);
            throw new MarketException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                    "Unable to process the request, please try again.");
        }
        return Response.ok().entity(
                MarketResponse.builder().withStatusCode(Response.Status.OK.getStatusCode())
                        .withBidVO(ObjectMapperUtil.bidVO(bid)).build()
        ).build();
    }

}
