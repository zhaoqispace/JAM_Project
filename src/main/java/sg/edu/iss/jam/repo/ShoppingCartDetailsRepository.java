package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import sg.edu.iss.jam.model.ShoppingCartDetails;

public interface ShoppingCartDetailsRepository extends JpaRepository<ShoppingCartDetails, Long> {

	@Transactional
	@Modifying
	@Query("Delete FROM ShoppingCartDetails u where u.shoppingCart.shoppingCartID =:shoppingCartID and u.product.productID =:productID")
	void deleteCartDetailsByID(@Param("productID") Long productID , @Param("shoppingCartID") Long shoppingCartID );

	
	@Query("SELECT sum(sd.quantity) FROM ShoppingCartDetails sd JOIN sd.shoppingCart s WHERE s.shoppingCartUser.userID = :userID")
	Long getItemCount(@Param("userID") long userid);

	@Query("SELECT u FROM ShoppingCartDetails u where u.shoppingCart.shoppingCartID =:shoppingCartID and u.product.productID =:productID")
	ShoppingCartDetails getByProductIdAndCartID(@Param("productID") Long productId,@Param("shoppingCartID") Long shoppingCartID);

}
