package sg.edu.iss.jam.service;

import java.util.List;

import javax.validation.Valid;

import sg.edu.iss.jam.model.Album;
import sg.edu.iss.jam.model.Comments;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.OrderDetails;
import sg.edu.iss.jam.model.Orders;
import sg.edu.iss.jam.model.Payment;
import sg.edu.iss.jam.model.Playlists;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.Roles;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.Tag;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.model.UserHistory;
import sg.edu.iss.jam.model.ShoppingCart;
import sg.edu.iss.jam.model.ShoppingCartDetails;

public interface UserInterface {

	// USER
	User findUserByUserId(Long userID);

	User saveUser(User user);

	// PLAYLISTS
	List<Playlists> findPlaylistsByUserId(Long userID);

	List<Playlists> findPlaylistByUserIdAndMediaType(Long userID, MediaType mediaType);

	Playlists findPlaylistByPlaylistID(long playlistID);

	Playlists savePlaylist(Playlists playlists);

	List<Playlists> savePlaylists(List<Playlists> playlists);

	// MEDIA
	Media findMediaByMediaId(Long ID);

	List<Media> findMediaListByPlayListID(Long playlistID);

	List<Media> findAllMedia();

	List<Media> findAllMediaByMediaType(MediaType mediaType);

	List<Media> findMediaByAlbumAndMediaType(Album album, MediaType mediaType);

	Media findMediaByMediaTypeAndMediaId(MediaType mediaType, Long id);

	Media saveMedia(Media media);

	// SUBSCRIBED
	Subscribed saveSubscribed(Subscribed subscribed);

	void deleteSubscribed(Subscribed s);

	List<Subscribed> getAllSubscribed();

	List<Subscribed> getArtistUnSubscribed(Long artistId);

	List<Subscribed> getArtistSubscribed(Long artistId);

	List<Subscribed> getArtistUnsubscribedByLoggInUserId(Long artistId, Long loggedInUserId);

	List<Subscribed> getArtistSubscribedByLoggInUserId(Long artistId, Long loggedInUserId);

	// COMMENTS
	List<Comments> findCommentsByMediaId(Long id);

	List<Comments> findCommentsByUserId(Long id);

	Comments saveComment(Comments comment);

	void removeComments(Long commentId);

	// TAGS
	List<Tag> findTagsByMediaId(Long id);

	// USER HISTORY
	List<UserHistory> findAllUserHistory();

	List<UserHistory> findUserHistoryByMediaId(Long id);

	ShoppingCart getShoppingCartByUserID(long userID);

	UserHistory saveUserHistory(UserHistory userHistory);

	Product findProduct(Long id);

	void removeCartDetails(Long productID, Long cartID);

	Long getItemCountByUserID(long artistid);

	void saveOrder(Orders neworder);

	void saveOrderDetailsList(List<OrderDetails> orderDetailList);

	void saveCartDetails(ShoppingCartDetails carddetail);

	ShoppingCartDetails getCartDetailByProductID(Long productId, Long shoppingCartID);

	void updateUser(User user);

	void savePayement(@Valid Payment payment);

	void deleteCartDetails(ShoppingCartDetails cardetail);

	void updateProduct(Product product);

	List<Object[]> getTopAllProductsInPastWeekByOrderDetailsQuantity(int i);

	List<Object[]> getTopMusicCollectionProductsInPastWeekByOrderDetailsQuantity(int i);

	List<Object[]> getTopMerchandiseProductsInPastWeekByOrderDetailsQuantity(int i);

	List<Object[]> getTopClothingProductsInPastWeekByOrderDetailsQuantity(int i);

	List<Object[]> getAllProducts();

	List<Object[]> getAllMusicCollections();

	List<Object[]> getAllClothing();

	List<Object[]> getAllMerchandise();

	List<Orders> getPurchaseHistoryByUserId(Long userID);

	List<Product> getListOfAllProduts();

	List<UserHistory> findUserHistoryByUserId(Long userId);

	List<UserHistory> findUserHistoryByUserIdAndMediaType(Long userId, MediaType mediaType);

	List<User> getAllUser();

	void saveRole(Roles b);

	List<User> getUserSubs(Long userID);

	List<User> getFollowing(Long userID);

	void updatePassword(User user, String newPassword);

	User get(String restPasswordToken);

	void updateResetPasswordToken(String token, String email) throws UserNotFoundException;

}
