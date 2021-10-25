package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

	@Query("SELECT s FROM ShoppingCart s where s.shoppingCartUser.userID = :userId")
	ShoppingCart getByUserID(@Param("userId") long userID);

}
