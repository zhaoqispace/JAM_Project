package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.OrderDetails;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {

	@Query("SELECT SUM(od.quantity) FROM OrderDetails od JOIN od.product p WHERE p.productID = :productID")
	public Long countbyProductId(@Param("productID") Long productID);
}