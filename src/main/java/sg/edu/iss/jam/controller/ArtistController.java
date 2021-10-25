package sg.edu.iss.jam.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sg.edu.iss.jam.DTO.AlbumDTO;
import sg.edu.iss.jam.DTO.ChannelDTO;
import sg.edu.iss.jam.DTO.MediaDTO;
import sg.edu.iss.jam.model.Album;
import sg.edu.iss.jam.model.Category;
import sg.edu.iss.jam.model.Channel;
import sg.edu.iss.jam.model.Media;
import sg.edu.iss.jam.model.MediaType;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.security.MyUserDetails;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.UploadInterface;
import sg.edu.iss.jam.service.UserInterface;

@Controller
@RequestMapping("/artist")
public class ArtistController {

	@Autowired
	ArtistInterface ArtistService;
	@Autowired
	UserInterface userService;
	@Autowired
	UploadInterface UploadService;

	@RequestMapping("/manageshop")
	public String manageShopAllProducts(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = userService.findUserByUserId(userDetails.getUserId());
		Map<Product, Long> productsAndCountShop = new HashMap<Product, Long>();

		Long count = userService.getItemCountByUserID(user.getUserID());
		List<Product> productsInShop = ArtistService.getProductListByArtistID(user.getUserID());

		for (Product product : productsInShop) {
			Long quantity = ArtistService.getQuantitySold(product.getProductID());
			productsAndCountShop.put(product, quantity);
		}
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("productsAndCountShop", productsAndCountShop);
		model.addAttribute("user", user);
		model.addAttribute("count", count);
		model.addAttribute("category", "allProducts");

		return "artistmanageshop";
	}

	@GetMapping("/manageshop/musiccollection")
	public String manageShopMusicCollection(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = userService.findUserByUserId(userDetails.getUserId());
		Map<Product, Long> productsAndCountShop = new HashMap<Product, Long>();

		Long count = userService.getItemCountByUserID(user.getUserID());
		List<Product> productsInShop = ArtistService.getProductListByArtistIDAndCategory(user.getUserID(),
				Category.MusicCollection);

		for (Product product : productsInShop) {
			Long quantity = ArtistService.getQuantitySold(product.getProductID());
			productsAndCountShop.put(product, quantity);
		}

		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("productsAndCountShop", productsAndCountShop);
		model.addAttribute("user", user);
		model.addAttribute("count", count);
		model.addAttribute("category", "musicCollection");

		return "artistmanageshop";
	}

	@GetMapping("/manageshop/merchandise")
	public String manageShopMerchandise(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = userService.findUserByUserId(userDetails.getUserId());
		Map<Product, Long> productsAndCountShop = new HashMap<Product, Long>();

		Long count = userService.getItemCountByUserID(user.getUserID());
		List<Product> productsInShop = ArtistService.getProductListByArtistIDAndCategory(user.getUserID(),
				Category.Merchandise);

		for (Product product : productsInShop) {
			Long quantity = ArtistService.getQuantitySold(product.getProductID());
			productsAndCountShop.put(product, quantity);
		}

		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("productsAndCountShop", productsAndCountShop);
		model.addAttribute("user", user);
		model.addAttribute("count", count);
		model.addAttribute("category", "merchandise");

		return "artistmanageshop";
	}

	@GetMapping("/manageshop/clothing")
	public String manageShopClothing(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = userService.findUserByUserId(userDetails.getUserId());
		Map<Product, Long> productsAndCountShop = new HashMap<Product, Long>();

		Long count = userService.getItemCountByUserID(user.getUserID());
		List<Product> productsInShop = ArtistService.getProductListByArtistIDAndCategory(user.getUserID(),
				Category.Clothing);

		for (Product product : productsInShop) {
			Long quantity = ArtistService.getQuantitySold(product.getProductID());
			productsAndCountShop.put(product, quantity);
		}

		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("productsAndCountShop", productsAndCountShop);
		model.addAttribute("user", user);
		model.addAttribute("count", count);
		model.addAttribute("category", "clothing");

		return "artistmanageshop";
	}

	@GetMapping("/addnewproduct")
	public String addNewProduct(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = userService.findUserByUserId(userDetails.getUserId());
		Map<Category, String> categories = new HashMap<Category, String>();
		Product newProduct = new Product();

		Long count = userService.getItemCountByUserID(user.getUserID());

		for (Category category : Category.values()) {
			if (category == Category.MusicCollection) {
				categories.put(category, "Music Collection");
			} else {
				categories.put(category, category.toString());
			}
		}

		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("newProduct", newProduct);
		model.addAttribute("user", user);
		model.addAttribute("count", count);
		model.addAttribute("categories", categories);

		return "addnewproduct";
	}

	@GetMapping("/editproduct")
	public String editProduct(@RequestParam("productID") Long productID, Model model,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		User user = userService.findUserByUserId(userDetails.getUserId());
		Map<Category, String> categories = new HashMap<Category, String>();

		Long count = userService.getItemCountByUserID(user.getUserID());
		Product product = ArtistService.getProductByID(productID);

		for (Category category : Category.values()) {
			if (category == Category.MusicCollection) {
				categories.put(category, "Music Collection");
			} else {
				categories.put(category, category.toString());
			}
		}

		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("product", product);
		model.addAttribute("user", user);
		model.addAttribute("count", count);
		model.addAttribute("categories", categories);

		return "editproduct";
	}

	@PostMapping("/saveproduct")
	public String saveProduct(@Valid @ModelAttribute("product") Product product,
			@RequestParam("file") Optional<MultipartFile> rawfile, BindingResult bindingResult, Model model,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		User user = userService.findUserByUserId(userDetails.getUserId());

		if (bindingResult.hasErrors()) {
			return "artist/editproduct";
		}

		product.setProductUser(ArtistService.findById(user.getUserID()));
		ArtistService.saveProduct(product);

		if (!rawfile.get().isEmpty()) {

			MultipartFile file = rawfile.get();
			Long productidtemp = product.getProductID();

			Path fileStorageLocation = Paths.get("src/main/resources/static/images/productimages");
			String filename = productidtemp.toString() + "_productimg."
					+ file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
			Path destinationFile = fileStorageLocation.resolve(Paths.get(filename)).toAbsolutePath();

			InputStream inputStream;
			try {
				inputStream = file.getInputStream();
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			product.setProductUrl("/images/productimages/" + filename);
		}

		ArtistService.saveProduct(product);
		return "redirect:/artist/manageshop";
	}

	@PostMapping(value = "/editShopDescription")
	@ResponseBody
	public void add(@RequestParam(value = "newShopDescription") String newShopDescription,
			@AuthenticationPrincipal MyUserDetails userDetails) throws Exception {

		User user = userService.findUserByUserId(userDetails.getUserId());

		user.setShopDescription(newShopDescription);
		userService.saveUser(user);
	}

	// --------------------MEDIA PORTION-----------------//

	// Get Channels
	@GetMapping("/channel")
	public String ViewChannels(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = userService.findUserByUserId(userDetails.getUserId());
		// get AristID(userID)
		Long userid = user.getUserID();

		Channel ChannelVideo = ArtistService.getChannelbyUserandMediaType(ArtistService.findById(userid),
				MediaType.Video);
		Channel ChannelMusic = ArtistService.getChannelbyUserandMediaType(ArtistService.findById(userid),
				MediaType.Music);

		int VideoCount = ArtistService.getMediaCountbyChannel(ChannelVideo.getChannelID(), MediaType.Video);
		int MusicCount = ArtistService.getMediaCountbyChannel(ChannelMusic.getChannelID(), MediaType.Music);

		// Repo-Channel Details and Sum up Channel (Create DTO?)
		ChannelDTO ChannelDTOVideo = new ChannelDTO(ChannelVideo, VideoCount, 0);
		ChannelDTO ChannelDTOMusic = new ChannelDTO(ChannelMusic, 0, MusicCount); // Do we want album count instead

		Collection<ChannelDTO> ChannelDTOlist = new ArrayList<ChannelDTO>();
		ChannelDTOlist.add(ChannelDTOVideo);
		ChannelDTOlist.add(ChannelDTOMusic);

		// Add to Model
		model.addAttribute("ChannelDTOlist", ChannelDTOlist);

		Long count = userService.getItemCountByUserID(user.getUserID());
		model.addAttribute("count", count);
		model.addAttribute("user", user);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("bannerUrl", user.getBannerUrl());
		model.addAttribute("userID", user.getUserID());

		return "channel/ChannelList.html";

	}

	// Post(edit) Channels
	@PostMapping("/channel/editchannel")
	public String EditChannel(@ModelAttribute("channel") @Validated Channel channeldto, BindingResult bindingResult,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		if (bindingResult.hasErrors()) {
			return "redirect:/artist/channel";
		}

		Channel channel = ArtistService.getChannel(channeldto.getChannelID());

		channel.setChannelName(channeldto.getChannelName());
		channel.setChannelDescription(channeldto.getChannelDescription());

		ArtistService.saveChannel(channel);
		return "redirect:/artist/channel";
	}

	// Get Channel Contents for Video Channel
	@GetMapping("channel/Video")
	public String ChannelContent(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = userService.findUserByUserId(userDetails.getUserId());
		// get AristID(userID)
		Long userid = user.getUserID();

		// get enum mediatype
		MediaType mediaType = MediaType.valueOf("Video");

		// get channelID
		Channel Channel = ArtistService.getChannelbyUserandMediaType(ArtistService.findById(userid), mediaType);

		// Get all media where channel=channelID
		Collection<Media> medias = ArtistService.getMedias(Channel.getChannelID());

		// Create Collection of MediaDTO
		Collection<MediaDTO> MediaDTOList = new ArrayList<MediaDTO>();

		// Add all media to MediaDTO, count view, count comments, concat all tags
		for (Iterator<Media> iterator = medias.iterator(); iterator.hasNext();) {
			Media Media = (Media) iterator.next();
			MediaDTOList.add(new MediaDTO(Media, ArtistService.getViewcountByMedia(Media),
					ArtistService.getCommentcountByMedia(Media), ArtistService.getTagsByMedia(Media)));
		}

		model.addAttribute("MediaDTOList", MediaDTOList);

		Long count = userService.getItemCountByUserID(user.getUserID());
		model.addAttribute("count", count);
		model.addAttribute("user", user);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("bannerUrl", user.getBannerUrl());

		return "channel/ChannelContentVideo.html";
	}

	// Post(edit) Channels
	@PostMapping("/channel/editvideo")
	public String EditMedia(@ModelAttribute("media") @Validated Media mediaDTO,
			@RequestPart("tags") Optional<String> tags,
			@RequestPart("thumbnailfile") Optional<MultipartFile> multipartFileThumbnail,
			@RequestPart("VideoFile") Optional<MultipartFile> multipartFileMedia, BindingResult bindingResult,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		if (bindingResult.hasErrors()) {
			return "error";
		}

		User user = userService.findUserByUserId(userDetails.getUserId());
		// get AristID(userID)
		Long userid = user.getUserID();
		// define enum mediatype
		MediaType mediaType = MediaType.valueOf("Video");
		// get channel
		Channel channel = ArtistService.getChannelbyUserandMediaType(ArtistService.findById(userid), mediaType);

		Media media;
		MultipartFile mediafile = multipartFileMedia.get();
		MultipartFile thumbFile = multipartFileThumbnail.get();

		String uploadDir = "src/main/resources/static/media/channel" + channel.getChannelID().toString() + "/video/";

		// New File Adding
		if (mediaDTO.getId() == null) {

			media = mediaDTO;
			media.setMediaType(MediaType.Video);
			media.setCreatedOn(LocalDate.now());
			media.setChannel(channel);
			if (!tags.isEmpty()) {
				media.setTagList(ArtistService.getTagsbytagName(tags.get())); // Cannot delete tags. Probably Cascade
																				// Problem
			}

			media = ArtistService.saveMedia(media);

			// When creating new video, file cannot be empty
			if (mediafile.getSize() > 0 && thumbFile.getSize() > 0) {
				media.setMediaUrl("/media/channel" + channel.getChannelID().toString() + "/video/"
						+ UploadService.store(mediafile, uploadDir, "video" + media.getId().toString() + "_video."));
				media.setThumbnailUrl("/media/channel" + channel.getChannelID().toString() + "/video/" + UploadService
						.store(thumbFile, uploadDir, "video" + media.getId().toString() + "_thumbnail."));
				media = ArtistService.saveMedia(media);
			} else {
				return "error";
			}

			// Editing Existing File
		} else {

			media = ArtistService.getMediabyid(mediaDTO.getId());
			media.setTitle(mediaDTO.getTitle());
			media.setDescription(mediaDTO.getDescription());
			media.setPublishStatus(mediaDTO.getPublishStatus());
			if (!tags.isEmpty()) {
				media.setTagList(ArtistService.getTagsbytagName(tags.get()));// Cannot delete tags. Probably Cascade
																				// Problem
			} else
				media.setTagList(null);

			if (mediafile.getSize() > 0) {
				media.setMediaUrl("/media/channel" + channel.getChannelID().toString() + "/video/"
						+ UploadService.store(mediafile, uploadDir, "video" + media.getId().toString() + "_video."));
			}
			if (thumbFile.getSize() > 0) {
				media.setThumbnailUrl("/media/channel" + channel.getChannelID().toString() + "/video/" + UploadService
						.store(thumbFile, uploadDir, "video" + media.getId().toString() + "_thumbnail."));
				// set duration using xuggler?
			}

			ArtistService.saveMedia(media);
		}

		return "redirect:/artist/channel/Video";
	}

	// Get(delete) Video
	@GetMapping("/channel/deletevideo/{mediaid}")
	public String deleteVideo(@PathVariable("mediaid") Long mediaID,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		User user = userService.findUserByUserId(userDetails.getUserId());
		// get AristID(userID)
		Long userid = user.getUserID();
		// get enum mediatype
		MediaType mediaType = MediaType.valueOf("Video");
		// get channel
		Channel channel = ArtistService.getChannelbyUserandMediaType(ArtistService.findById(userid), mediaType);
		// get Media
		Media media = ArtistService.getMediabyid(mediaID);
		// Check if Video belongs to Artist
		if (ArtistService.checkifMediaAuthorised(channel, media) == false) {
			return "error";
		}

		// Delete
		try {
			UploadService.delete(media.getMediaUrl().toString());
			UploadService.delete(media.getThumbnailUrl().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArtistService.deleteMedia(media);

		return "redirect:/artist/channel/Video";
	}

	// View Channel Contents for Album
	@GetMapping("channel/Music")
	public String viewAlbums(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = userService.findUserByUserId(userDetails.getUserId());
		// get AristID(userID)
		Long userid = user.getUserID();

		// get enum mediatype
		MediaType mediaType = MediaType.valueOf("Music");

		// get ChannelID
		Channel Channel = ArtistService.getChannelbyUserandMediaType(ArtistService.findById(userid), mediaType);

		// get List of albums (added to next line)
		Collection<Album> albumlist = ArtistService.getAlbumsbyChannel(Channel.getChannelID());

		Collection<AlbumDTO> albumDTOlist = new ArrayList<AlbumDTO>();

		for (Album album : albumlist) {
			albumDTOlist.add(new AlbumDTO(album, ArtistService.getMediaCountbyAlbum(album.getAlbumID()),
					ArtistService.getViewcountByAlbum(album.getAlbumID())));
		}

		// Add list of albums to model
		model.addAttribute("AlbumDTOList", albumDTOlist);

		Long count = userService.getItemCountByUserID(user.getUserID());
		model.addAttribute("count", count);
		model.addAttribute("user", user);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("bannerUrl", user.getBannerUrl());

		return "channel/ChannelContentMusic";
	}

	// POST(Edit) Albums
	@PostMapping("channel/Music/editalbum")
	public String EditAlbums(@ModelAttribute("album") @Validated Album albumdto,
			@RequestPart("AlbumCover") Optional<MultipartFile> multipartFileAlbumCover, BindingResult bindingResult,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		if (bindingResult.hasErrors()) {
			return "error";
		}

		User user = userService.findUserByUserId(userDetails.getUserId());
		// get AristID(userID)
		Long userid = user.getUserID();

		Album album;
		MultipartFile Albumcoverfile = multipartFileAlbumCover.get();
		// define enum mediatype
		MediaType mediaType = MediaType.Music;
		// get channel
		Channel channel = ArtistService.getChannelbyUserandMediaType(ArtistService.findById(userid), mediaType);

		if (albumdto.getAlbumID() == null) {

			album = albumdto;
			album.setChannel(channel);
			album = ArtistService.saveAlbum(album);
			String uploadDir = "src/main/resources/static/media/channel" + channel.getChannelID().toString()
					+ "/Music/album" + album.getAlbumID().toString();
			if (Albumcoverfile.getSize() > 0 && Albumcoverfile.getSize() > 0) {
				album.setAlbumImgURL("/media/channel" + channel.getChannelID().toString() + "/Music/album"
						+ album.getAlbumID().toString() + "/" + UploadService.store(Albumcoverfile, uploadDir,
								"albumcover" + album.getAlbumID().toString() + "."));
				album = ArtistService.saveAlbum(album);
			} else
				return "error";

		} else {

			album = ArtistService.getAlbumbyID(albumdto.getAlbumID());
			album.setAlbumDescription(albumdto.getAlbumDescription());
			album = ArtistService.saveAlbum(album);

			String uploadDir = "src/main/resources/static/media/channel" + channel.getChannelID().toString()
					+ "/Music/album" + album.getAlbumID().toString();
			if (Albumcoverfile.getSize() > 0 && Albumcoverfile.getSize() > 0) {
				album.setAlbumImgURL("/media/channel" + channel.getChannelID().toString() + "/Music/album"
						+ album.getAlbumID().toString() + "/" + UploadService.store(Albumcoverfile, uploadDir,
								"albumcover" + album.getAlbumID().toString() + "."));
				album = ArtistService.saveAlbum(album);
			}

		}

		return "redirect:/artist/channel/Music";
	}

	// View Album Contents for Music
	@GetMapping("channel/Music/{albumid}")
	public String Music(Model model, @PathVariable("albumid") Long albumid,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		User user = userService.findUserByUserId(userDetails.getUserId());
		// get AristID(userID)
		Long userid = user.getUserID();

		// get enum mediatype
		MediaType mediaType = MediaType.valueOf("Music");
		// get ChannelID
		Channel Channel = ArtistService.getChannelbyUserandMediaType(ArtistService.findById(userid), mediaType);
		// get Album
		Album album = ArtistService.getAlbumbyID(albumid);

		// Check if AlbumID belongs to Artist, else return unauthorized
		if (!ArtistService.CheckifAlbumAuthorised(album, Channel)) {
			return "error";
		}

		// get List of Songs in order
		List<Media> songs = ArtistService.getMusicbyAlbum(album.getAlbumID());

		// Create Collection of MediaDTO
		Collection<MediaDTO> MediaDTOList = new ArrayList<MediaDTO>();

		// Add all media to MediaDTO, count view, count comments, concat all tags
		for (Iterator<Media> iterator = songs.iterator(); iterator.hasNext();) {
			Media Media = (Media) iterator.next();
			MediaDTOList.add(new MediaDTO(Media, ArtistService.getViewcountByMedia(Media),
					ArtistService.getCommentcountByMedia(Media), ArtistService.getTagsByMedia(Media)));
		}

		model.addAttribute("album", album);
		model.addAttribute("MediaDTOList", MediaDTOList);

		Long count = userService.getItemCountByUserID(user.getUserID());
		model.addAttribute("count", count);
		model.addAttribute("user", user);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("bannerUrl", user.getBannerUrl());

		return "channel/ChannelContentAlbum.html";
	}

	// POST(Edit/Add Music)
	@PostMapping("channel/Music/editmusic/{albumid}")
	public String EditMusic(Model model, @ModelAttribute("media") @Validated Media mediaDTO,
			@RequestPart("tags") Optional<String> tags,
			@RequestPart("MediaFile") Optional<MultipartFile> multipartFileMedia, @PathVariable("albumid") Long albumid,
			BindingResult bindingResult, @AuthenticationPrincipal MyUserDetails userDetails) {

		if (bindingResult.hasErrors()) {
			return "error";
		}

		User user = userService.findUserByUserId(userDetails.getUserId());
		// get AristID(userID)
		Long userid = user.getUserID();
		// get enum mediatype
		MediaType mediaType = MediaType.valueOf("Music");
		// get ChannelID
		Channel channel = ArtistService.getChannelbyUserandMediaType(ArtistService.findById(userid), mediaType);
		// get Album
		Album album = ArtistService.getAlbumbyID(albumid);

		String uploadDir = "src/main/resources/static/media/channel" + channel.getChannelID().toString()
				+ "/music/album" + albumid;

		Media media;
		MultipartFile mediafile = multipartFileMedia.get();

		// New File Adding
		if (mediaDTO.getId() == null) {

			media = mediaDTO;
			media.setAlbum(album);
			media.setMediaType(mediaType);
			media.setCreatedOn(LocalDate.now());
			media.setChannel(channel);
			media.setThumbnailUrl(album.getAlbumImgURL());
			if (!tags.isEmpty()) {
				media.setTagList(ArtistService.getTagsbytagName(tags.get())); // Having Problem saving tags due to
																				// ownership

			}

			media = ArtistService.saveMedia(media);

			// When creating new video, file cannot be empty
			if (mediafile.getSize() > 0) {
				media.setMediaUrl("/media/channel" + channel.getChannelID().toString() + "/Music/album"
						+ album.getAlbumID().toString() + "/"
						+ UploadService.store(mediafile, uploadDir, "music" + media.getId().toString() + "."));
				media = ArtistService.saveMedia(media);
			} else {
				return "error";
			}

			// Editing Existing File
		} else {

			media = ArtistService.getMediabyid(mediaDTO.getId());
			media.setAlbum(album);
			media.setTitle(mediaDTO.getTitle());
			media.setDescription(mediaDTO.getDescription());
			media.setPublishStatus(mediaDTO.getPublishStatus());
			media.setAlbumOrder(mediaDTO.getAlbumOrder());
			if (!tags.isEmpty()) {
				media.setTagList(ArtistService.getTagsbytagName(tags.get()));

			} else
				media.setTagList(null);

			if (mediafile.getSize() > 0) {
				media.setMediaUrl("/media/channel" + channel.getChannelID().toString() + "/Music/album"
						+ album.getAlbumID().toString() + "/"
						+ UploadService.store(mediafile, uploadDir, "music" + media.getId().toString() + "."));
			}

			ArtistService.saveMedia(media);

		}
		return "redirect:/artist/channel/Music/" + albumid;
	}

	// Delete music
	@GetMapping("/channel/deletemusic/{AlbumID}/{mediaid}")
	public String deleteMusic(@PathVariable("mediaid") Long mediaID, @PathVariable("AlbumID") Long AlbumID,
			@AuthenticationPrincipal MyUserDetails userDetails) {

		User user = userService.findUserByUserId(userDetails.getUserId());
		// get AristID(userID)
		Long userid = user.getUserID();
		// get enum mediatype
		MediaType mediaType = MediaType.valueOf("Music");
		// get channel
		Channel channel = ArtistService.getChannelbyUserandMediaType(ArtistService.findById(userid), mediaType);
		// get Media
		Media media = ArtistService.getMediabyid(mediaID);
		// Check if Video belongs to Artist
		if (ArtistService.checkifMediaAuthorised(channel, media) == false) {
			return "error";
		}

		// Delete
		if (media.getMediaUrl() != null) {
			try {
				UploadService.delete(media.getMediaUrl().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ArtistService.deleteMedia(media);

		return "redirect:/artist/channel/Music/" + AlbumID;
	}

}
