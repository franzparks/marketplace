package francis.market.project.constant;

public final class ProjectQuery {
    private ProjectQuery() {
    }

    public static final String Project_ID = "from Project where id = :id";
    public static final String ALL_Projects = "from Project project where parsedateTime(project.endTime, 'yyyy-MM-dd') >= " +
            "parsedateTime(now(), 'yyyy-MM-dd')";
    public static final String IS_Project_EXIST = "Select count(*) from Project where id = :ProjectId";
    public static final String LOWEST_BID = "select bid from Bid bid where bid.project.id = :ProjectId and bid.price = " +
            "(select min(b.price) from Bid b where b.project.id = :ProjectId)";
}
