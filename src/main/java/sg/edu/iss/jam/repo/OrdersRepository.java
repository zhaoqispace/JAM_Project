package sg.edu.iss.jam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.Orders;

public interface OrdersRepository extends JpaRepository<Orders, Long> {

	@Query("SELECT DISTINCT o FROM Orders o JOIN o.orderDetails od WHERE o.user.userID = :userID ORDER BY o.orderID desc")
	List<Orders> getPurchaseHistoryByUserId(@Param("userID") Long userID);

}
