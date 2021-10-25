package sg.edu.iss.jam.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import sg.edu.iss.jam.model.Album;
import sg.edu.iss.jam.model.Category;
import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.Subscribed;
import sg.edu.iss.jam.model.Tag;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.repo.AlbumRepository;
import sg.edu.iss.jam.repo.ChannelRepository;
import sg.edu.iss.jam.repo.CommentsRepository;
import sg.edu.iss.jam.repo.MediaRepository;
import sg.edu.iss.jam.repo.OrderDetailsRepository;
import sg.edu.iss.jam.repo.ProductRepository;
import sg.edu.iss.jam.repo.SubscribedRepository;
import sg.edu.iss.jam.repo.TagRepository;
import sg.edu.iss.jam.repo.UserHistoryRepository;
import sg.edu.iss.jam.repo.UserRepository;

@Service
public class ArtistImplementation implements ArtistInterface {

	@Autowired
	UserRepository urepo;

	@Autowired
	SubscribedRepository subrepo;

	@Autowired
	ProductRepository prepo;

	@Autowired
	OrderDetailsRepository odrepo;

	@Autowired
	ChannelRepository channelrepo;

	@Autowired
	MediaRepository mediarepo;

	@Autowired
	CommentsRepository commentrepo;

	@Autowired
	TagRepository tagrepo;

	@Autowired
	UserHistoryRepository historyrepo;

	@Autowired
	AlbumRepository albumrepo;

	@Autowired
	AlbumRepository arepo;

	@Transactional
	public User findById(Long userID) {

		return urepo.findById(userID).get();
	}

	@Transactional
	public User saveUser(User user) {
		// TODO Auto-generated method stub
		return urepo.save(user);
	}

	@Transactional
	public Subscribed saveSubscribed(Subscribed subscribed) {
		// TODO Auto-generated method stub
		return subrepo.save(subscribed);
	}

	@Transactional
	public void deleteSubscribed(Subscribed s) {
		subrepo.delete(s);
	}

	@Override
	public List<Product> getProductListByArtistID(long artistid) {
		// TODO Auto-generated method stub
		return prepo.getProductListByArtistID(artistid);
	}

	@Override
	public User getArtistByID(long artistid) {
		// TODO Auto-generated method stub
		return urepo.getById(artistid);
	}

	@Transactional
	public void saveProduct(Product product) {
		prepo.save(product);
	}

	@Transactional
	public Album getAlbumByAlbumId(long AlbumId) {
		return albumrepo.findById(AlbumId).get();
	}

	@Override
	public long getQuantitySold(Long productID) {
		if (odrepo.countbyProductId(productID) == null) {
			return (long) 0;
		} else {
			return odrepo.countbyProductId(productID);
		}
	}

	@Override
	public Product getProductByID(long productid) {
		// TODO Auto-generated method stub
		return prepo.getById(productid);
	}

	@Override
	public List<Product> getProductListByArtistIDAndCategory(long artistid, Category category) {
		return prepo.getProductListByArtistIDAndCategory(artistid, category);
	}

	@Override
	public List<Object[]> getTopAllProductsInPastWeekByOrderDetailsQuantity(int i) {
		return prepo.getTopProductsByOrderDetailsQuantity(PageRequest.of(0, i), LocalDate.now().minusWeeks(1));
	}

	@Override
	public List<Product> getProductListByArtistIDAll(Long userID) {
		return prepo.getProductListByArtistIDAll(userID);
	}

	@Override
	public Channel getChannel(Long channelID) {

		return channelrepo.findById(channelID).get();
	}

	@Override
	public Channel saveChannel(Channel channel) {

		return channelrepo.save(channel);
	}

	@Override
	public Collection<Media> getMedias(Long channelID) {

		Channel channel = channelrepo.findById(channelID).get();

		Collection<Media> medias = mediarepo.findBychannel(channel);

		return medias;
	}

	@Override
	public int getCommentcountByMedia(Media Media) {

		return commentrepo.CountCommentsByMedia(Media.getId());
	}

	@Override
	public int getViewcountByMedia(Media Media) {

		return historyrepo.CountViewsByMedia(Media.getId());
	}

	@Override
	public String getTagsByMedia(Media Media) {

		Collection<Tag> taglist = tagrepo.findByMediaTagList(Media);

		String Concat = "";

		for (Iterator iterator = taglist.iterator(); iterator.hasNext();) {
			Tag tag = (Tag) iterator.next();
			Concat = Concat + tag.getTagName() + ", ";
		}

		return Concat;
	}

	@Override
	public List<Product> getPopularProductByCategory(long artistid, Category category) {
		// TODO Auto-generated method stub
		return prepo.getPopularProductByCategory(artistid, category);
	}

	@Override
	public Collection<Album> getAlbumsbyChannel(Long channelID) {

		return arepo.findBychannelID(channelID);
	}

	@Override
	public List<Media> getMusicbyAlbum(Long AlbumID) {
		// TODO Auto-generated method stub
		return mediarepo.findByalbum(AlbumID);
	}

	@Override
	public boolean CheckifAlbumAuthorised(Album album, Channel channel) {

		if (album.getChannel() == channel) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Channel getChannelbyUserandMediaType(User user, MediaType mediaType) {
		// TODO Auto-generated method stub
		return channelrepo.findByChannelUserAndMediaType(user, mediaType);
	}

	@Override
	public int getMediaCountbyChannel(Long ChannelID, MediaType mediaType) {

		return mediarepo.CountMediaByChannel(ChannelID, mediaType);
	}

	@Override
	public Album getAlbumbyID(Long AlbumID) {
		// TODO Auto-generated method stub
		return arepo.findById(AlbumID).get();
	}

	@Override
	public Media getMediabyid(Long MediaID) {
		// TODO Auto-generated method stub
		return mediarepo.getById(MediaID);
	}

	@Override
	public Media saveMedia(Media Media) {
		// TODO Auto-generated method stub
		return mediarepo.save(Media);
	}

	@Override
	public void deleteMedia(Media Media) {
		mediarepo.deleteById(Media.getId());
	}

	@Override
	public boolean checkifMediaAuthorised(Channel channel, Media media) {

		if (media.getChannel() == channel) {
			return true;
		} else
			return false;

	}

	@Override
	public String saveTags(String tags, Long MediaID) {

		String[] tagsarray = tags.split(",");

		for (String tagstring : tagsarray) {

			tagstring = tagstring.trim();

			if (tagrepo.findByTagName(tagstring) == null) {

				Tag tag = new Tag();
				tag.setTagName(tagstring);
				tag.getMediaTagList().add(mediarepo.getById(MediaID));
				tagrepo.save(tag);
			} else {

				Tag tag = tagrepo.findByTagName(tagstring);
				tag.getMediaTagList().add(mediarepo.getById(MediaID));
				tagrepo.save(tag);

			}

		}

		return null;
	}

	@Override
	public Set<Tag> getTagsbytagName(String TagNames) {

		String[] tagsarray = TagNames.split(",");

		List<String> list = new ArrayList<String>();

		for (String s : tagsarray) {
			if (s != null && s.trim().length() > 0) {
				list.add(s.trim());
			}
		}

		tagsarray = list.toArray(new String[list.size()]);
		Set<Tag> TagCollection = new HashSet<Tag>();

		for (String tagstring : tagsarray) {
			tagstring = tagstring.trim();
			if (tagrepo.findByTagName(tagstring) == null) {
				Tag tag = new Tag();
				tag.setTagName(tagstring);
				TagCollection.add(tag);

			} else {
				Tag tag = tagrepo.findByTagName(tagstring);
				TagCollection.add(tag);
			}

		}

		return TagCollection;
	}

	@Override
	public int getMediaCountbyAlbum(Long AlbumID) {
		// TODO Auto-generated method stub
		return mediarepo.CountMediaByAlbum(AlbumID);
	}

	@Override
	public int getViewcountByAlbum(Long AlbumID) {
		// TODO Auto-generated method stub
		return historyrepo.CountViewsByAlbum(AlbumID);
	}

	@Override
	public Album saveAlbum(Album Album) {
		// TODO Auto-generated method stub
		return arepo.save(Album);
	}

}
