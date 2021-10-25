package sg.edu.iss.jam.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Roles {
	@Override
	public String toString() {
		return role.toString();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	//relation with user
	
	@ManyToOne
	private User roleUser;

	public Roles() {
		super();
	}

	public Roles(Role role) {
		super();
		this.role = role;
	}
	
	public Roles(Role role, User roleUser) {
		super();
		this.role = role;
		this.roleUser = roleUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getRoleUser() {
		return roleUser;
	}

	public void setRoleUser(User roleUser) {
		this.roleUser = roleUser;
	}
	
	
}
