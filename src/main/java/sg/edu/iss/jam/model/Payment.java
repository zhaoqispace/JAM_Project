package sg.edu.iss.jam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentID;
	
	@NotNull(message = "First Name cannot be null.")
	private String firstName;
	
	@NotNull(message = "Last Name cannot be null.")
	private String lastName;
	
	private String email;
	
	@NotNull(message = "Country is required")
	private String country;
	
	@NotNull(message = "Address is required")
	private String address;
	
	@NotNull(message = "City is required")
	private String city;
	
	private String postalCode;
	
	@NotNull(message = "Name on Card is required")
	private String nameOnCard;
	
	private String phoneNumber;
	
	@Pattern(regexp = "^4[0-9]{12}(?:[0-9]{3})?$", message = "Only Visa cards are accepted.")
	private String creditNumber;
	
	@Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$")
	private String expiration;
	
	@Digits(integer = 3, fraction = 0, message = "Invalid CVV")
	private String cvv;
	
	//relation with user
	@ManyToOne
	private User user;

	
	public Payment() {
		super();
	}

	public Payment(@NotNull(message = "First Name cannot be null.")String firstName, @NotNull(message = "Last Name cannot be null.") String lastName, String email, @NotNull(message = "Country is required")String country,@NotNull(message = "Address is required") String address, @NotNull(message = "City is required") String city,
			String postalCode,@NotNull(message = "Name on Card is required") String nameOnCard, String phoneNumber,@Pattern(regexp = "^4[0-9]{12}(?:[0-9]{3})?$", message = "Only Visa cards are accepted.") String creditNumber,@Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$" , message = "Only expiration date are accepted.") String expiration,
			@Digits(integer = 3, fraction = 0, message = "Invalid CVV") String cvv, User user) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.country = country;
		this.address = address;
		this.city = city;
		this.postalCode = postalCode;
		this.nameOnCard = nameOnCard;
		this.phoneNumber = phoneNumber;
		this.creditNumber = creditNumber;
		this.expiration = expiration;
		this.cvv = cvv;
		this.user = user;
	}

	public Long getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(Long paymentID) {
		this.paymentID = paymentID;
	}

	public String getCreditNumber() {
		return creditNumber;
	}

	public void setCreditNumber(String creditNumber) {
		this.creditNumber = creditNumber;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}


}
