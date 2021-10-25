package sg.edu.iss.jam.service;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import sg.edu.iss.jam.model.Album;
import sg.edu.iss.jam.model.Category;
import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.Tag;
import sg.edu.iss.jam.model.User;

public interface ArtistInterface {
	
	User findById(Long userID);
	
	User saveUser(User user);
	
	Subscribed saveSubscribed(Subscribed subscribed);

	void deleteSubscribed(Subscribed s);

	List<Product> getProductListByArtistID(long userID);

	User getArtistByID(long artistid);
	
	void saveProduct(Product product);
	
	long getQuantitySold(Long productID);

	Product getProductByID(long productid);

	List<Product> getProductListByArtistIDAndCategory(long artistid, Category category);
	

	Album getAlbumByAlbumId(long AlbumID);
	
	Channel getChannel(Long channelID);
	
	Channel saveChannel(Channel channel);
	
	Collection<Media> getMedias(Long channelID);
	
	boolean CheckifAlbumAuthorised(Album album, Channel channel);
	
	Channel getChannelbyUserandMediaType(User user,MediaType mediaType);
	
	int getCommentcountByMedia(Media Media);
	
	int getViewcountByMedia(Media Media);
	
	int getViewcountByAlbum(Long AlbumID);
	
	int getMediaCountbyChannel(Long ChannelID, MediaType mediaType);
	
	int getMediaCountbyAlbum(Long AlbumID);
	
	Collection<Album> getAlbumsbyChannel(Long channelID);
	
	List<Media> getMusicbyAlbum(Long AlbumID);
	
	Media getMediabyid(Long MediaID);
	
	Media saveMedia(Media Media);
	
	void deleteMedia(Media Media);
	
	boolean checkifMediaAuthorised(Channel channel, Media media);
	
	Album getAlbumbyID(Long AlbumID);
	
	Album saveAlbum(Album Album);
	
	String getTagsByMedia(Media Media);
	
	String saveTags(String tags, Long MediaID);

	List<Object[]> getTopAllProductsInPastWeekByOrderDetailsQuantity(int i);

	List<Product> getPopularProductByCategory(long artistid, Category category);
	
	Set<Tag> getTagsbytagName(String TagName);

	List<Product> getProductListByArtistIDAll(Long userID);
	
}
