package sg.edu.iss.jam.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class ShoppingCartDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartDetailID;
	
	private int quantity;
	
	@ManyToOne
	private Product product;
	
	@ManyToOne
	private ShoppingCart shoppingCart;
	

	public ShoppingCartDetails() {
		super();
	}
	
	public ShoppingCartDetails(int quantity, Product product, ShoppingCart shoppingCart) {
		super();
		this.quantity = quantity;
		this.product = product;
		this.shoppingCart = shoppingCart;
	}

	public Long getCartDetailID() {
		return cartDetailID;
	}

	public void setCartDetailID(Long cartDetailID) {
		this.cartDetailID = cartDetailID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	public double getAmount() {
        return this.product.getProductPrice() * this.quantity;
    }
	
	
	
	

}
