package sg.edu.iss.jam.model;

import java.time.LocalDate;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Orders {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long orderID;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate orderDate;

	private String address;

	// ManyToMany relation with user
	@ManyToOne
	private User user;

	@OneToMany (mappedBy="order")
	private Collection<OrderDetails> orderDetails;

	public Orders() {
		super();
	}

	public Orders(LocalDate orderDate, String address, User user, Collection<OrderDetails> orderDetails) {
		super();
		this.orderDate = orderDate;
		this.address = address;
		this.user = user;
		this.orderDetails = orderDetails;
	}


	public Long getOrderID() {
		return orderID;
	}

	public void setOrderID(Long orderID) {
		this.orderID = orderID;
	}

	
	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Collection<OrderDetails> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(Collection<OrderDetails> orderDetails) {
		this.orderDetails = orderDetails;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
