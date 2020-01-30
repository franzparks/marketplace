package francis.market.project.repository.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import francis.market.project.constant.ProjectQuery;
import francis.market.project.entity.Bid;
import francis.market.project.entity.Project;
import francis.market.project.repository.ProjectRepository;

/**
 * Project Repository implementation class. Both Project and Bid database operations go here.
 */
@Repository
public class ProjectRepositoryImpl implements ProjectRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public Project create(Project Project) {
        sessionFactory.getCurrentSession().save(Project);
        return Project;
    }

    @Override
    public Project detail(String id) {
        Query query = sessionFactory.getCurrentSession().createQuery(ProjectQuery.Project_ID);
        query.setString("id", id);
        return (Project) query.uniqueResult();
    }

    @Override
    public boolean isProjectExist(String id) {
        Query query = sessionFactory.getCurrentSession().createSQLQuery(ProjectQuery.IS_Project_EXIST);
        query.setString("ProjectId", id);
        BigInteger count = (BigInteger) query.uniqueResult();
        return count.intValue() > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Project> list() {
        return (List<Project>) sessionFactory.getCurrentSession().createQuery(ProjectQuery.ALL_Projects).list();
    }

    @Override
    public synchronized Bid bid(Bid bid) {
        sessionFactory.getCurrentSession().save(bid);
        return bid;
    }

    @Override
    public Bid getLatestBid(String ProjectId) {
        Query query = sessionFactory.getCurrentSession().createQuery(ProjectQuery.LOWEST_BID);
        query.setString("ProjectId", ProjectId);
        return (Bid) query.uniqueResult();
    }
}
