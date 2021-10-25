package sg.edu.iss.jam.model;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userID;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private String dateOfBirth;
	
	private String displayName;
	
	private String about;
	
	private String profileUrl;
	
	private String bannerUrl;
	
	private String shopDescription;
	
	private boolean enabled;
	
	private boolean isArtist;
	
//	@Enumerated(EnumType.STRING)
//	private Role role;
	
	@Column(name="rest_password_token")
	private String resetPasswordToken;
	
	//OneToMany relation with Roles
	@OneToMany(mappedBy = "roleUser", fetch= FetchType.EAGER)
	private Collection<Roles> roles;
	
	//OneToMany relation with Sessions
	@OneToMany(mappedBy = "sessionUser")
	private Collection<Sessions> sessions;
	
	//ManyToMany relation with Orders
	@OneToMany(mappedBy = "user")
	private Collection<Orders> orders;
	
	//oneToOne relation with wishlist
	@OneToOne(mappedBy = "wishlistUser")  
	private Wishlist wishlist;
	
	//relation with product 
	@OneToMany(mappedBy = "productUser")
	private Collection<Product> products;
		
	//OneToOne relation with shoppingcart
	@OneToOne(mappedBy = "shoppingCartUser")
	private ShoppingCart shoppingCart;
	
	//OneToMany relation with userhistory
	@OneToMany(mappedBy = "historyUser")
	private Collection<UserHistory> histories;
	
	//OneToMany relation with playlists
	@OneToMany(mappedBy = "playlistUser")
	private Collection<Playlists> playlists;
	
	//OneToMany relation with subscribed
	@OneToMany(mappedBy = "artist")
	private Collection<Subscribed> targetArtists;

	@OneToMany(mappedBy = "subscriber")
	private Collection<Subscribed> subscribers;
	
	//OneToMany relation with channel
	@OneToMany(mappedBy = "channelUser")
	private Collection<Channel> channels;
	
	
	//OneToMany relation with comment
	@OneToMany(mappedBy = "commentUser")
	private Collection<Comments> comments;
	
	//OneToMany relation with payment
	@OneToMany(mappedBy = "user")
	private Collection<Payment> paymentList;
	
	//OneToMany relation with comment
	@OneToMany(mappedBy = "user")
	private Collection<Post> posts;

	public User() {
		super();
	}

	public User(String firstName, String lastName, String email, String password, String dateOfBath, String displayName,
			String about, String profileUrl, String bannerUrl, Collection<Roles> roles) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.dateOfBirth = dateOfBath;
		this.displayName = displayName;
		this.about = about;
		this.profileUrl = profileUrl;
		this.bannerUrl = bannerUrl;
		this.roles = roles;
	}

	public User(String firstName, String lastName, String email, String password, String dateOfBirth,
			String displayName, String about, String profileUrl, boolean enabled, boolean isArtist) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.displayName = displayName;
		this.about = about;
		this.profileUrl = profileUrl;
		this.enabled = enabled;
		this.isArtist = isArtist;
	}

	public Long getUserID() {
		return userID;
	}


	public void setUserID(Long userID) {
		this.userID = userID;
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


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getDateOfBath() {
		return dateOfBirth;
	}


	public void setDateOfBath(String dateOfBath) {
		this.dateOfBirth = dateOfBath;
	}


	public String getDisplayName() {
		return displayName;
	}


	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}


	public String getAbout() {
		return about;
	}


	public void setAbout(String about) {
		this.about = about;
	}


	public String getProfileUrl() {
		return profileUrl;
	}
	
	public String getFullname() {
		String fullName = firstName + " " + lastName;
		
		return fullName;
	}


	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}


	public Collection<Roles> getRoles() {
		return roles;
	}
	
	public String getRole() {
		
		String role = roles.stream()
			.map(x->x.toString())
			.collect(Collectors.joining(" ")).toString();
			
			return role;
	}


	public void setRoles(Collection<Roles> roles) {
		this.roles = roles;
	}


	public Collection<Sessions> getSessions() {
		return sessions;
	}


	public void setSessions(Collection<Sessions> sessions) {
		this.sessions = sessions;
	}


	public Collection<Orders> getOrders() {
		return orders;
	}


	public void setOrders(Collection<Orders> orders) {
		this.orders = orders;
	}


	public Wishlist getWishlist() {
		return wishlist;
	}


	public void setWishlist(Wishlist wishlist) {
		this.wishlist = wishlist;
	}


	public Collection<Product> getProducts() {
		return products;
	}


	public void setProducts(Collection<Product> products) {
		this.products = products;
	}


	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}


	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}


	public Collection<UserHistory> getHistories() {
		return histories;
	}


	public void setHistories(Collection<UserHistory> histories) {
		this.histories = histories;
	}


	public Collection<Playlists> getPlaylists() {
		return playlists;
	}


	public void setPlaylists(Collection<Playlists> playlists) {
		this.playlists = playlists;
	}

	public Collection<Subscribed> getTargetArtists() {
		return targetArtists;
	}

	public void setTargetArtists(Collection<Subscribed> targetArtists) {
		this.targetArtists = targetArtists;
	}

	public Collection<Subscribed> getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(Collection<Subscribed> subscribers) {
		this.subscribers = subscribers;
	}

	public Collection<Channel> getChannels() {
		return channels;
	}


	public void setChannels(Collection<Channel> channels) {
		this.channels = channels;
	}


	public Collection<Comments> getComments() {
		return comments;
	}


	public void setComments(Collection<Comments> comments) {
		this.comments = comments;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = true;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getShopDescription() {
		return shopDescription;
	}

	public void setShopDescription(String shopDescription) {
		this.shopDescription = shopDescription;
	}

	public Collection<Payment> getPaymentList() {
		return paymentList;
	}

	public void setPaymentList(Collection<Payment> paymentList) {
		this.paymentList = paymentList;
	}

	public boolean isArtist() {
		return isArtist;
	}

	public void setArtist(boolean isArtist) {
		this.isArtist = isArtist;
	}

	public String getBannerUrl() {
		return bannerUrl;
	}

	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}

	public String getRestPasswordToken() {
		return resetPasswordToken;
	}

	public void setRestPasswordToken(String restPasswordToken) {
		this.resetPasswordToken = restPasswordToken;
	}
	
	

//	public Role getRole() {
//		return role;
//	}
//
//	public void setRole(Role role) {
//		this.role = role;
//	}
	
	
	
	

}
