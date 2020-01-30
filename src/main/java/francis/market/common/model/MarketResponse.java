package francis.market.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Value Object class used to return responses. All resources/Apis must return this instance as a response.
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@Getter
public final class MarketResponse {

    private int statusCode;
    private String message;
    private Set<ErrorVO> errorVOs;
    private Set<ProjectVO> ProjectVOs;
    private UserVO userVO;
    private BidVO bidVO;

    private MarketResponse(Builder builder) {
        this.statusCode = builder.statusCode;
        this.message = builder.message;
        this.errorVOs = builder.errorVOs;
        this.ProjectVOs = builder.ProjectVOs;
        this.userVO = builder.userVO;
        this.bidVO = builder.bidVO;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int statusCode;
        private String message;
        private Set<ErrorVO> errorVOs;
        private Set<ProjectVO> ProjectVOs;
        private UserVO userVO;
        private BidVO bidVO;

        public final MarketResponse build() {
            return new MarketResponse(this);
        }

        public Builder withStatusCode(int statusCode) {
            this.statusCode = statusCode;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withErrorVO(ErrorVO errorVO) {
            if (this.errorVOs == null) {
                this.errorVOs = new HashSet<>();
            }
            this.errorVOs.add(errorVO);
            return this;
        }

        public Builder withProjectVO(ProjectVO ProjectVO) {
            if (this.ProjectVOs == null) {
                this.ProjectVOs = new HashSet<>();
            }
            this.ProjectVOs.add(ProjectVO);
            return this;
        }

        public Builder withProjectVO(Set<ProjectVO> ProjectVOs) {
            if (this.ProjectVOs == null) {
                this.ProjectVOs = new HashSet<>();
            }
            this.ProjectVOs.addAll(ProjectVOs);
            return this;
        }

        public Builder withUserVO(UserVO userVO) {
            this.userVO = userVO;
            return this;
        }
        
        public Builder withBidVO(BidVO bidVO) {
            this.bidVO = bidVO;
            return this;
        }
    }
}
