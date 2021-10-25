package sg.edu.iss.jam.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import sg.edu.iss.jam.model.Category;
import sg.edu.iss.jam.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	@Query("SELECT c FROM Product c WHERE c.productUser.userID = :artistid AND c.productCategory = :category")
	List<Product> getProductListByArtistIDAndCategory(@Param("artistid") long artistid,
			@Param("category") Category category);

	@Query("SELECT c FROM Product c WHERE c.productUser.userID = :artistid and c.productQty >0")
	List<Product> getProductListByArtistID(long artistid);
	
	@Query("SELECT c FROM Product c WHERE c.productUser.userID = :artistid")
	List<Product> getProductListByArtistIDAll(long artistid);

	@Query("SELECT p, SUM(od.quantity) FROM Product p LEFT JOIN p.orderDetails od WHERE od.order.orderDate >= DATE(:currentdatelessoneweek) AND p.productQty > 0 GROUP BY p ORDER BY SUM(od.quantity) DESC")
	List<Object[]> getTopProductsByOrderDetailsQuantity(Pageable pageable,
			@Param("currentdatelessoneweek") LocalDate currentdatelessoneweek);

	@Query("SELECT c FROM Product c WHERE c.productUser.userID = :artistid AND c.productCategory = :category")
	List<Product> getPopularProductByCategory(@Param("artistid") long artistid, @Param("category") Category category);

	@Query("SELECT p, SUM(od.quantity) FROM Product p LEFT JOIN p.orderDetails od WHERE od.order.orderDate >= DATE(:currentdatelessoneweek) AND p.productCategory = :category AND p.productQty > 0 GROUP BY p ORDER BY SUM(od.quantity) DESC")
	List<Object[]> getTopProductsByCategoryInPastWeekByOrderDetailsQuantity(Pageable pageable,
			@Param("currentdatelessoneweek") LocalDate currentdatelessoneweek, @Param("category") Category category);

	@Query("SELECT p, SUM(od.quantity) FROM Product p LEFT JOIN p.orderDetails od WHERE p.productCategory = :category GROUP BY p ORDER BY SUM(od.quantity) DESC")
	List<Object[]> getAllProductsAndQuantityByCategory(@Param("category") Category category);

	@Query("SELECT p, SUM(od.quantity) FROM Product p LEFT JOIN p.orderDetails od GROUP BY p ORDER BY SUM(od.quantity) DESC")
	List<Object[]> getAllProductsAndQuantity();
}
