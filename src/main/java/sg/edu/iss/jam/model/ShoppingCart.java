package sg.edu.iss.jam.model;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;



@Entity
public class ShoppingCart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long shoppingCartID;
	
	//OneToOne relation with user
	@OneToOne
	private User shoppingCartUser;
	
	//relation with shoppingcartDetail
	@OneToMany(mappedBy="shoppingCart")
	private Collection<ShoppingCartDetails> cartDetails;

	public ShoppingCart() {
		super();
	}

	public ShoppingCart(User shoppingCartUser, Collection<ShoppingCartDetails> cartDetails) {
		super();
		this.shoppingCartUser = shoppingCartUser;
		this.cartDetails = cartDetails;
	}



	public Long getShoppingCartID() {
		return shoppingCartID;
	}

	public void setShoppingCartID(Long shoppingCartID) {
		this.shoppingCartID = shoppingCartID;
	}

	public User getShoppingCartUser() {
		return shoppingCartUser;
	}

	public void setShoppingCartUser(User shoppingCartUser) {
		this.shoppingCartUser = shoppingCartUser;
	}


	public Collection<ShoppingCartDetails> getCartDetails() {
		return cartDetails;
	}


	public void setCartDetails(Collection<ShoppingCartDetails> cartDetails) {
		this.cartDetails = cartDetails;
	}

	
	public double getAmountTotal() {
        double total = 0;
        for (ShoppingCartDetails line : this.cartDetails) {
            total += line.getAmount();
        }
        return total;
    }
	
	public int getQuantityTotal() {
        int quantity = 0;
        for (ShoppingCartDetails line : this.cartDetails) {
            quantity += line.getQuantity();
        }
        return quantity;
    }
	
	public void updateQuantity(ShoppingCart cartForm) {
        if (cartForm != null) {
            Collection<ShoppingCartDetails> lines = cartForm.getCartDetails();
            for (ShoppingCartDetails line : lines) {
                this.updateProduct(line.getProduct().getProductID(), line.getQuantity());
            }
        }
 
    }
	
	public void updateProduct(Long id, int quantity) {
        ShoppingCartDetails line = this.findLineByID(id);
 
        if (line != null) {
            if (quantity <= 0) {
                this.cartDetails.remove(line);
            } else {
                line.setQuantity(quantity);
            }
        }
    }
	
	private ShoppingCartDetails findLineByID(Long id) {
        for (ShoppingCartDetails line : this.cartDetails) {
            if (line.getProduct().getProductID().equals(id)) {
                return line;
            }
        }
        return null;
    }
	
	
	 public void removeProduct(Product productInfo) {
	        ShoppingCartDetails line = this.findLineByID(productInfo.getProductID());
	        if (line != null) {
	            this.cartDetails.remove(line);
	        }
	    }
	 
}
