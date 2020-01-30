package francis.market.project.service.impl;

import francis.market.project.entity.Bid;
import francis.market.project.entity.Project;
import francis.market.project.exception.BidNotAllowedException;
import francis.market.project.exception.InvalidBidAmountException;
import francis.market.project.exception.ProjectNotFoundException;
import francis.market.project.repository.ProjectRepository;
import francis.market.project.service.ProjectService;
import francis.market.user.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * ProjectService implementation
 */
@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository ProjectRepository;

    /**
     * Create new Project.
     * Set the project time to current time.
     *
     * @param Project
     * @return
     * @throws UserNotFoundException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Project create(Project Project) {
        Project.setStartTime(new Date());
        ProjectRepository.create(Project);
        log.info("Project record saved in db with id: {}", Project.getId());
        return Project;
    }

    /**
     * Returns the Project info for specified project id.
     * throws <code>ProjectNotFoundException</code> if project not exist.
     *
     * @param id
     * @return
     * @throws ProjectNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public Project projectDetails(String id) throws ProjectNotFoundException {
        log.info("Fetching the project id: {}", id);
        Project Project = ProjectRepository.detail(id);
        if (Project == null) {
            throw new ProjectNotFoundException("Project not found .");
        }
        //to avoid lazy initialization exception.
        Project.getUser().getEmail();
        Project.getCurrentLowestBid().getPrice();
        return Project;
    }

    /**
     * Returns List Projects.
     * Throws <code>ProjectNotFoundException</code> if zero Projects.
     *
     * @return
     * @throws ProjectNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public List<Project> list() throws ProjectNotFoundException {
        List<Project> projects = ProjectRepository.list();
        if (CollectionUtils.isEmpty(projects)) {
            throw new ProjectNotFoundException("Projects not found.");
        }
        log.info("Fetched # of Projects: {}", projects.size());
        //To Avoid lazy initialization exception.
        for (Project Project : projects) {
            Project.getUser().getEmail();
            Bid lowestBid = Optional.ofNullable(Project.getCurrentLowestBid()).orElse(null);
            if (lowestBid != null) {
                Project.setCurrentLowestBid(lowestBid);
            }

        }
        //TODO: limit to 100
        return projects;
    }

    /**
     * Post bid on project.
     * Throws <code>ProjectNotFoundException</code>, when the requested project id does not exist.
     * Throws <code>InvalidBidAmountException</code>, if the Bid price is not less than existing bid for the project.
     *
     * @param ProjectId
     * @param bid
     * @return
     * @throws ProjectNotFoundException
     * @throws InvalidBidAmountException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Bid bid(String ProjectId, Bid bid) throws ProjectNotFoundException, InvalidBidAmountException, BidNotAllowedException {
        Project Project = ProjectRepository.detail(ProjectId);
        if (Project == null) {
            throw new ProjectNotFoundException("Project not found.");
        } else if (bid.getUser().getEmail().equalsIgnoreCase(Project.getUser().getEmail())) {
            throw new BidNotAllowedException("Bid not allowed.");
        } else {
            boolean isBidPriceLess = false;
            if (Project.getCurrentLowestBid() != null) {
                isBidPriceLess = Project.getCurrentLowestBid().getPrice().compareTo(bid.getPrice()) >= 0;
            }

            if (!isBidPriceLess) {
                bid.setTime(new Date());
                bid.setProject(Project);
                ProjectRepository.bid(bid);
                log.info("Bid record saved in db with id: {}", bid.getId());
            } else {
                throw new InvalidBidAmountException("Bid price is Invalid.");
            }
            //TODO: add more validity checks for a bid
        }
        return bid;
    }

    /**
     * Get the Latest Bid.
     * Returns the bid which has lowest price.
     * Throws <code>ProjectNotFoundException</code>, when the project id not exist.
     *
     * @param projectId
     * @return
     * @throws ProjectNotFoundException
     */
    @Override
    @Transactional(readOnly = true)
    public Bid getLatestBid(String projectId) throws ProjectNotFoundException {
        if (!ProjectRepository.isProjectExist(projectId)) {
            throw new ProjectNotFoundException("Project not found.");
        } else {
            Bid bid = ProjectRepository.getLatestBid(projectId);
            bid.getUser().getEmail();
            bid.getProject().getProjectId();
            return bid;
        }
    }
}
