package francis.market.user.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import francis.market.project.entity.Bid;
import francis.market.project.entity.Project;
import org.hibernate.annotations.GenericGenerator;

import lombok.Getter;
import lombok.Setter;

/**
 * USER
 */
@Entity
@Table(name = "USER")
@Setter @Getter
public class User implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2708646471073046443L;

	@Id
    @Column(name = "ID")
    @GenericGenerator(strategy = "uuid2", name = "uuid")
    @GeneratedValue(generator = "uuid")
    private String id;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @Column(name = "CONTACT", nullable = false)
    private String contact;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "ADDRESS")
    private String address;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Project> Projects;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Bid> bids;
}