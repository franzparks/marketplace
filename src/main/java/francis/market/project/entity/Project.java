package francis.market.project.entity;

import francis.market.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

/**
 * Entity class mapped to Project data table.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "Project")
@Setter
@Getter
public class Project implements Serializable {

	@Id
	@Column(name = "ID")
	@GenericGenerator(strategy = "uuid2", name = "uuid")
	@GeneratedValue(generator = "uuid")
	private String id;

	@Column(name = "START_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	@Column(name = "END_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	@Column(name = "PRICE", nullable = false)
	private BigDecimal price;

	@Column(name = "PROJECT_ID", nullable = false, length = 25)
	private String projectId;

	@Column(name = "PROJECT_NAME", nullable = false, length = 25)
	private String projectName;

	@Column(name = "PROJECT_DESC", length = 256)
	private String projectDesc;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false, referencedColumnName = "ID")
	private User user;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "project")
	private Bid currentLowestBid;
}
