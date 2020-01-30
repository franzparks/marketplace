package francis.market.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * ProjectVO  Value Object/DTO to transfer Project info as a Json message.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public final class ProjectVO {

    private String id;
    private Date startTime;
    @NotNull(message = "EndTime should not be blank")
    private Date endTime;
    @NotNull(message = "Price should not be blank")
    private BigDecimal price;
    @NotBlank(message = "ProjectId should not be blank")
    private String projectId;
    @NotBlank(message = "ProjectName should not be blank")
    private String projectName;
    @NotBlank(message = "ProjectType should not be blank")
    private String projectDesc;
    private UserVO user;
    private BidVO currentLowestBid;

    public ProjectVO(Builder builder) {
        this.id = builder.id;
        this.user = builder.userVO;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.price = builder.price;
        this.projectId = builder.projectId;
        this.projectName = builder.projectName;
        this.projectDesc = builder.projectDesc;
        this.currentLowestBid = builder.currentLowestBid;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private UserVO userVO;
        private Date startTime;
        private Date endTime;
        private BigDecimal price;
        private String projectId;
        private String projectName;
        private String projectDesc;
        private BidVO currentLowestBid;

        public Builder() {
        }

        public ProjectVO build() {
            return new ProjectVO(this);
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withStartTime(Date startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder withEndTime(Date endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder withPrice(BigDecimal initialPrice) {
            this.price = initialPrice;
            return this;
        }

        public Builder withProjectId(String id) {
            this.projectId = id;
            return this;
        }

        public Builder withProjectName(String name) {
            this.projectName = name;
            return this;
        }

        public Builder withProjectDesc(String desc) {
            this.projectDesc = desc;
            return this;
        }

        public Builder withUserVO(UserVO userVO) {
            this.userVO = userVO;
            return this;
        }

        public Builder withCurrentLowestBid(BidVO bid) {
            this.currentLowestBid = bid;
            return this;
        }
    }
}
