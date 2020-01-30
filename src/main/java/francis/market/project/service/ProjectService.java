package francis.market.project.service;

import francis.market.project.entity.Bid;
import francis.market.project.entity.Project;
import francis.market.project.exception.BidNotAllowedException;
import francis.market.project.exception.InvalidBidAmountException;
import francis.market.project.exception.ProjectNotFoundException;
import francis.market.user.exception.UserNotFoundException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ProjectService
 */
public interface ProjectService {

    Project create(Project Project);

    Project projectDetails(String id) throws ProjectNotFoundException;

    List<Project> list() throws ProjectNotFoundException;

    Bid bid(String ProjectId, Bid bid) throws ProjectNotFoundException, InvalidBidAmountException, BidNotAllowedException;

    Bid getLatestBid(String ProjectId) throws ProjectNotFoundException;
}
