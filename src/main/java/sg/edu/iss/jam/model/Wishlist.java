package sg.edu.iss.jam.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Wishlist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long wishListID;
	
	
	//relation with user
	@OneToOne
	private User wishlistUser;
	
	//ManyToMany relation with product 
	@ManyToMany(mappedBy = "wishlists")
	private Collection<Product> products;

	public Wishlist() {
		super();
	}

	public Wishlist(User wishlistUser, Collection<Product> products) {
		super();
		this.wishlistUser = wishlistUser;
		this.products = products;
	}

	public Long getWishListID() {
		return wishListID;
	}

	public void setWishListID(Long wishListID) {
		this.wishListID = wishListID;
	}

	public User getWishlistUser() {
		return wishlistUser;
	}

	public void setWishlistUser(User wishlistUser) {
		this.wishlistUser = wishlistUser;
	}

	public Collection<Product> getProducts() {
		return products;
	}

	public void setProducts(Collection<Product> products) {
		this.products = products;
	}
	
	

}
