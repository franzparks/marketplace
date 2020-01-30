package francis.market.common.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Bid Value Object/DTO
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor
@EqualsAndHashCode
public class BidVO {

    private String id;
    private UserVO user;
    private ProjectVO Project;
    @NotNull(message = "Price should not be blank")
    private BigDecimal price;
    private Date time;

    public BidVO(Builder builder) {
        this.id = builder.id;
        this.user = builder.user;
        this.Project = builder.Project;
        this.price = builder.price;
        this.time = builder.time;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private UserVO user;
        private ProjectVO Project;
        private BigDecimal price;
        private Date time;

        public Builder() {
        }

        public BidVO build() {
            return new BidVO(this);
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withTime(Date time) {
            this.time = time;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        public Builder withUser(UserVO user) {
            this.user = user;
            return this;
        }

        public Builder withProject(ProjectVO Project) {
            this.Project = Project;
            return this;
        }
    }
}
