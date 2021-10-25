package sg.edu.iss.jam.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.Album;
import sg.edu.iss.jam.model.Category;
import sg.edu.iss.jam.model.Comments;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.OrderDetails;
import sg.edu.iss.jam.model.Orders;
import sg.edu.iss.jam.model.Payment;
import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.Roles;
import sg.edu.iss.jam.model.ShoppingCart;
import sg.edu.iss.jam.model.ShoppingCartDetails;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.Tag;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.model.UserHistory;
import sg.edu.iss.jam.repo.CommentsRepository;
import sg.edu.iss.jam.repo.MediaRepository;
import sg.edu.iss.jam.repo.OrderDetailsRepository;
import sg.edu.iss.jam.repo.OrdersRepository;
import sg.edu.iss.jam.repo.PaymentRepository;
import sg.edu.iss.jam.repo.PlaylistsRepository;
import sg.edu.iss.jam.repo.ProductRepository;
import sg.edu.iss.jam.repo.RolesRepository;
import sg.edu.iss.jam.repo.ShoppingCartDetailsRepository;
import sg.edu.iss.jam.repo.ShoppingCartRepository;
import sg.edu.iss.jam.repo.SubscribedRepository;
import sg.edu.iss.jam.repo.TagRepository;
import sg.edu.iss.jam.repo.UserHistoryRepository;
import sg.edu.iss.jam.repo.UserRepository;

@Service
public class UserImplementation implements UserInterface {

	@Autowired
	UserRepository urepo;

	@Autowired
	PlaylistsRepository plrepo;

	@Autowired
	MediaRepository mediarepo;

	@Autowired
	SubscribedRepository subrepo;

	@Autowired
	TagRepository tagrepo;

	@Autowired
	CommentsRepository commentsrepo;

	@Autowired
	UserHistoryRepository uhrepo;

	@Autowired
	ShoppingCartRepository shrepo;

	@Autowired
	ShoppingCartDetailsRepository shdrepo;

	@Autowired
	ProductRepository prepo;

	@Autowired
	OrdersRepository orepo;

	@Autowired
	OrderDetailsRepository odrepo;

	@Autowired
	PaymentRepository payrepo;

	@Autowired
	RolesRepository rrepo;

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	// USER REPO
	@Transactional
	public User findUserByUserId(Long userID) {
		return urepo.findById(userID).get();
	}

	@Transactional
	public User saveUser(User user) {
		return urepo.save(user);
	}

	// PLAYLISTS REPO
	@Transactional
	public List<Playlists> findPlaylistsByUserId(Long userID) {
		return plrepo.findPlaylistsByUserId(userID);
	}

	@Transactional
	public List<Playlists> findPlaylistByUserIdAndMediaType(Long userID, MediaType mediaType) {
		return plrepo.findPlaylistByUserIdAndMediaType(userID, mediaType);
	}

	@Transactional
	public List<Playlists> savePlaylists(List<Playlists> playlists) {
		return plrepo.saveAll(playlists);
	}

	@Transactional
	public Playlists findPlaylistByPlaylistID(long playlistID) {

		return plrepo.findPlaylistByPlaylistID(playlistID);
	}

	@Transactional
	public Playlists savePlaylist(Playlists playlists) {
		return plrepo.save(playlists);
	}

	// MEDIA REPO
	@Transactional
	public Media findMediaByMediaId(Long ID) {
		return mediarepo.getById(ID);
	}

	@Transactional
	public List<Media> findMediaListByPlayListID(Long playlistID) {
		return mediarepo.findMediaListByPlayListID(playlistID);
	}

	@Transactional
	public List<Media> findAllMedia() {
		return mediarepo.findAll();
	}

	@Transactional
	public List<Media> findAllMediaByMediaType(MediaType mediaType) {
		return mediarepo.findAllMediaByMediaType(mediaType);
	}

	@Transactional
	public Media saveMedia(Media media) {
		return mediarepo.save(media);
	}

	@Transactional
	public Media findMediaByMediaTypeAndMediaId(MediaType mediaType, Long id) {
		return mediarepo.findMediaByMediaTypeAndMediaId(mediaType, id);
	}

	@Transactional
	public List<Media> findMediaByAlbumAndMediaType(Album album, MediaType mediaType) {
		return mediarepo.findMediaByAlbumAndMediaType(album, mediaType);
	}

	// SUBSCRIBED REPO
	@Transactional
	public Subscribed saveSubscribed(Subscribed subscribed) {
		return subrepo.save(subscribed);
	}

	@Transactional
	public void deleteSubscribed(Subscribed s) {
		subrepo.delete(s);
	}

	@Transactional
	public List<Subscribed> getAllSubscribed() {
		return subrepo.findAll();
	}

	@Transactional
	public List<Subscribed> getArtistSubscribed(Long artistId) {
		return subrepo.getArtistSubscribed(artistId);
	}

	@Transactional
	public List<Subscribed> getArtistUnSubscribed(Long artistId) {
		return subrepo.getArtistUnSubscribed(artistId);
	}

//	@Transactional
//	public List<Subscribed> getArtistSubscribedUnsubscribed(Long userID) {
//		return subrepo.getArtistSubscribedUnsubscribed(userID);
//	}

	@Override
	public List<Subscribed> getArtistUnsubscribedByLoggInUserId(Long artistId, Long loggedInUserId) {
		// TODO Auto-generated method stub
		return subrepo.getArtistUnsubscribedByLoggInUserId(artistId, loggedInUserId);
	}

	@Override
	public List<Subscribed> getArtistSubscribedByLoggInUserId(Long artistId, Long loggedInUserId) {
		// TODO Auto-generated method stub
		return subrepo.getArtistSubscribedByLoggInUserId(artistId, loggedInUserId);
	}

	// TAG REPO
	@Transactional
	public List<Tag> findTagsByMediaId(Long id) {
		return tagrepo.findTagsByMediaId(id);
	}

	// COMMENTS REPO
	@Transactional
	public List<Comments> findCommentsByMediaId(Long id) {
		return commentsrepo.findCommentsByMediaId(id);
	}

	@Transactional
	public List<Comments> findCommentsByUserId(Long id) {
		return commentsrepo.findCommentsByUserId(id);
	}

	@Transactional
	public Comments saveComment(Comments comment) {
		return commentsrepo.save(comment);
	}

	@Transactional
	public void removeComments(Long commentId) {
		commentsrepo.deleteById(commentId);
	}

	// USERHISTORY REPO
	@Transactional
	public List<UserHistory> findUserHistoryByMediaId(Long id) {

		return uhrepo.findUserHistoryByMediaId(id);
	}

	@Transactional
	public UserHistory saveUserHistory(UserHistory userHistory) {
		return uhrepo.save(userHistory);
	}

	@Transactional
	public ShoppingCart getShoppingCartByUserID(long userID) {
		// TODO Auto-generated method stub
		return shrepo.getByUserID(userID);
	}

	@Override
	public Product findProduct(Long id) {
		// TODO Auto-generated method stub
		return prepo.getById(id);
	}

	@Override
	public void removeCartDetails(Long productID, Long cartID) {
		// TODO Auto-generated method stub
		shdrepo.deleteCartDetailsByID(productID, cartID);
	}

	@Override
	public List<Object[]> getTopAllProductsInPastWeekByOrderDetailsQuantity(int i) {
		return prepo.getTopProductsByOrderDetailsQuantity(PageRequest.of(0, i), LocalDate.now().minusWeeks(1));
	}

	@Override
	public List<Object[]> getTopMusicCollectionProductsInPastWeekByOrderDetailsQuantity(int i) {
		return prepo.getTopProductsByCategoryInPastWeekByOrderDetailsQuantity(PageRequest.of(0, i),
				LocalDate.now().minusWeeks(1), Category.MusicCollection);
	}

	@Override
	public List<Object[]> getTopMerchandiseProductsInPastWeekByOrderDetailsQuantity(int i) {
		return prepo.getTopProductsByCategoryInPastWeekByOrderDetailsQuantity(PageRequest.of(0, i),
				LocalDate.now().minusWeeks(1), Category.Merchandise);
	}

	@Override
	public List<Object[]> getTopClothingProductsInPastWeekByOrderDetailsQuantity(int i) {
		return prepo.getTopProductsByCategoryInPastWeekByOrderDetailsQuantity(PageRequest.of(0, i),
				LocalDate.now().minusWeeks(1), Category.Clothing);
	}

	@Override
	public List<Object[]> getAllProducts() {
		return prepo.getAllProductsAndQuantity();
	}

	@Override
	public List<Object[]> getAllMusicCollections() {
		return prepo.getAllProductsAndQuantityByCategory(Category.MusicCollection);
	}

	@Override
	public List<Object[]> getAllClothing() {
		return prepo.getAllProductsAndQuantityByCategory(Category.Clothing);
	}

	@Override
	public List<Object[]> getAllMerchandise() {
		return prepo.getAllProductsAndQuantityByCategory(Category.Merchandise);
	}

	@Override
	public Long getItemCountByUserID(long artistid) {
		// TODO Auto-generated method stub
		return shdrepo.getItemCount(artistid);
	}

	@Override
	public void saveOrder(Orders neworder) {
		// TODO Auto-generated method stub
		orepo.save(neworder);
	}

	@Override
	public void saveOrderDetailsList(List<OrderDetails> orderDetailList) {
		// TODO Auto-generated method stub
		odrepo.saveAll(orderDetailList);
	}

	@Override
	public void saveCartDetails(ShoppingCartDetails carddetail) {
		// TODO Auto-generated method stub
		shdrepo.save(carddetail);
	}

	@Override
	public ShoppingCartDetails getCartDetailByProductID(Long productId, Long shoppingCartID) {
		// TODO Auto-generated method stub
		return shdrepo.getByProductIdAndCartID(productId, shoppingCartID);
	}

	@Override
	public void updateUser(User user) {
		urepo.save(user);
	}

	@Override
	public void savePayement(@Valid Payment payment) {
		// TODO Auto-generated method stub
		payrepo.save(payment);
	}

	@Override
	public void deleteCartDetails(ShoppingCartDetails cardetail) {
		// TODO Auto-generated method stub
		shdrepo.delete(cardetail);
	}

	@Override
	public void updateProduct(Product product) {
		// TODO Auto-generated method stub
		prepo.save(product);
	}

	@Override
	public List<Orders> getPurchaseHistoryByUserId(Long userID) {
		return orepo.getPurchaseHistoryByUserId(userID);
	}

	@Override
	public List<Product> getListOfAllProduts() {
		return prepo.findAll();
	}

	@Override
	public List<UserHistory> findUserHistoryByUserId(Long userId) {

		return uhrepo.findUserHistoryByUserId(userId);
	}

	@Override
	public List<UserHistory> findUserHistoryByUserIdAndMediaType(Long userId, MediaType mediaType) {

		return uhrepo.findUserHistoryByUserIdAndMediaType(userId, mediaType);
	}

	public List<UserHistory> findAllUserHistory() {
		return uhrepo.findAll();
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return urepo.findAll();
	}

	@Override
	public void saveRole(Roles b) {
		// TODO Auto-generated method stub
		rrepo.save(b);
	}

	@Override
	public List<User> getUserSubs(Long userID) {

		User user = findUserByUserId(userID);
		List<Subscribed> subUsers = new ArrayList<>();
		List<User> result = new ArrayList<>();
		List<Subscribed> allSubAndUnsub = subrepo.findAllFollowingByArtistId(userID);
		List<Subscribed> temp = new ArrayList<>();

		for (int i = 0; i < allSubAndUnsub.size(); i++) {
			for (int j = 0; j < allSubAndUnsub.size(); j++) {
				if (allSubAndUnsub.get(i).getSubscriber().getUserID() == allSubAndUnsub.get(j).getSubscriber()
						.getUserID()) {
					temp.add(allSubAndUnsub.get(j));
				}
			}
			if (temp.size() == 1) {
				subUsers.add(temp.get(0));
				temp.clear();
			} else {
				Subscribed latest = Collections.max(temp, Comparator.comparing(x -> x.getTimeSubscribed()));
				List<Subscribed> sorted = temp.stream()
						.sorted(Comparator.comparing(Subscribed::getTimeSubscribed).reversed())
						.collect(Collectors.toList());
				if (sorted.get(0).isSubscribed()) {
					subUsers.add(sorted.get(0));
				}
			}
			temp.clear();
		}

		for (Subscribed s : subUsers) {
			result.add(s.getSubscriber());
		}

		return result.stream().filter(distinctByKey(x -> x.getUserID())).collect(Collectors.toList());
	}

	@Override
	public List<User> getFollowing(Long userID) {

		User user = findUserByUserId(userID);
		List<Subscribed> followUsers = new ArrayList<>();
		List<User> result = new ArrayList<>();
		List<Subscribed> allFollowAndUnfollow = subrepo.findAllSubscribedBySubId(userID);
		List<Subscribed> temp = new ArrayList<>();

		for (int i = 0; i < allFollowAndUnfollow.size(); i++) {
			for (int j = 0; j < allFollowAndUnfollow.size(); j++) {
				if (allFollowAndUnfollow.get(i).getArtist().getUserID() == allFollowAndUnfollow.get(j).getArtist()
						.getUserID()) {
					temp.add(allFollowAndUnfollow.get(j));
				}
			}
			if (temp.size() == 1) {
				followUsers.add(temp.get(0));
				temp.clear();
			} else {
				Subscribed latest = Collections.max(temp, Comparator.comparing(x -> x.getTimeSubscribed()));
				List<Subscribed> sorted = temp.stream()
						.sorted(Comparator.comparing(Subscribed::getTimeSubscribed).reversed())
						.collect(Collectors.toList());

				if (sorted.get(0).isSubscribed()) {
					followUsers.add(sorted.get(0));
				}
			}

			temp.clear();
		}

		for (Subscribed s : followUsers) {
			result.add(s.getArtist());
		}

		return result.stream().filter(distinctByKey(x -> x.getUserID())).collect(Collectors.toList());
	}

	@Override
	public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
		User user = urepo.findByEmail(email);

		if (user != null) {
			user.setRestPasswordToken(token);
			urepo.save(user);
		} else {
			throw new UserNotFoundException("Could not find any user with email: " + email);
		}
	}

	@Override
	public User get(String restPasswordToken) {
		return urepo.findByResetPasswordToken(restPasswordToken);
	}

	@Override
	public void updatePassword(User user, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodePassword = passwordEncoder.encode(newPassword);

		user.setPassword(encodePassword);
		user.setRestPasswordToken(null);
		urepo.save(user);

	}

}
