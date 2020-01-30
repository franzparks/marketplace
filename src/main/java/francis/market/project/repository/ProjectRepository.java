package francis.market.project.repository;

import francis.market.project.entity.Bid;
import francis.market.project.entity.Project;

import java.util.List;

/**
 * JPA repository class, to perform all Project related CRUD operations and lookup.
 */
public interface ProjectRepository {
	Project create(Project Project);

	Project detail(String id);

	boolean isProjectExist(String id);

	List<Project> list();

	Bid bid(Bid bid);

	Bid getLatestBid(String ProjectId);
}
