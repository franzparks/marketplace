package francis.market.project.util;

import francis.market.common.model.BidVO;
import francis.market.common.model.ProjectVO;
import francis.market.common.model.UserVO;
import francis.market.project.entity.Bid;
import francis.market.project.entity.Project;
import francis.market.user.entity.User;
import org.springframework.beans.BeanUtils;

/**
 * Object conversion util class, used to convert Entity to Model and vice versa.
 */
public final class ObjectMapperUtil {

    public static Project ProjectEntity(ProjectVO ProjectVO) {
        Project ProjectEntity = new Project();
        BeanUtils.copyProperties(ProjectVO, ProjectEntity);
        return ProjectEntity;
    }

    public static ProjectVO ProjectVO(Project projectEntity) {
        ProjectVO projectVO = ProjectVO.builder()
                .withId(projectEntity.getId())
                .withStartTime(projectEntity.getStartTime())
                .withEndTime(projectEntity.getEndTime())
                .withPrice(projectEntity.getPrice())
                .withProjectId(projectEntity.getProjectId())
                .withProjectName(projectEntity.getProjectName())
                .withProjectDesc(projectEntity.getProjectDesc())
                .withUserVO(
                    user(projectEntity.getUser())
                ).withCurrentLowestBid(
                    bidVO(projectEntity.getCurrentLowestBid())
            ).build();
        return projectVO;
    }

    public static Bid bidEntity(BidVO bidVO) {
        Bid bid = new Bid();
        BeanUtils.copyProperties(bidVO, bid);
        return bid;
    }

    public static BidVO bidVO(Bid bid) {
        BidVO bidVO = BidVO.builder()
                .withId(bid.getId())
                .withPrice(bid.getPrice())
                .withTime(bid.getTime())
                .withUser(user(bid.getUser()))
                .withProject(
                        ProjectVO.builder().withId(bid.getProject().getId())
                                .withPrice(bid.getProject().getPrice())
                                .withProjectName(bid.getProject().getProjectName())
                                .withStartTime(bid.getProject().getStartTime())
                                .withEndTime(bid.getProject().getEndTime())
                                .build()
                ).build();

        return bidVO;
    }

    private static UserVO user(User user) {
        return UserVO.builder().withEmail(user.getEmail())
                .withContact(user.getContact())
                .withFirstName(user.getFirstName()).build();
    }

}
