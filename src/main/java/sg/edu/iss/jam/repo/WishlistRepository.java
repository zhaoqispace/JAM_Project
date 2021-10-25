package sg.edu.iss.jam.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import sg.edu.iss.jam.model.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

}
