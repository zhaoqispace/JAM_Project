package sg.edu.iss.jam.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.edu.iss.jam.model.Category;
import sg.edu.iss.jam.model.OrderDetails;
import sg.edu.iss.jam.model.Orders;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.security.MyUserDetails;
import sg.edu.iss.jam.service.ArtistInterface;
import sg.edu.iss.jam.service.UserInterface;

@Controller
public class ProductController {
	@Autowired
	ArtistInterface aservice;

	@Autowired
	UserInterface uservice;

	@GetMapping("/carthometab/{artistid}")
	public String shoppingCartHome(Model model, @PathVariable long artistid,
			@AuthenticationPrincipal MyUserDetails userDetails) {
		User artist = null;
		artist = aservice.getArtistByID(artistid);

		User user = null;
		user = aservice.getArtistByID(userDetails.getUserId());

		// userId need to replace
		Long count = uservice.getItemCountByUserID(userDetails.getUserId());
		model.addAttribute("artistId", artistid);
		model.addAttribute("status", "allProducts");
		model.addAttribute("user", user);
		model.addAttribute("count", count);
		model.addAttribute("artistId", artistid);
		model.addAttribute("artist", artist);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("productList", aservice.getProductListByArtistID(artistid));
		return "carthometab";
	}

	@GetMapping("/carthometab/{artistid}/{category}")
	public String getProductByCategory(Model model, @PathVariable long artistid, @PathVariable Category category,
			@AuthenticationPrincipal MyUserDetails userDetails) {
		User artist = null;
		artist = aservice.getArtistByID(artistid);
		User user = null;
		user = aservice.getArtistByID(userDetails.getUserId());
		// userId need to replace
		Long count = uservice.getItemCountByUserID(userDetails.getUserId());
		model.addAttribute("artistId", artistid);
		model.addAttribute("status", category.toString());
		model.addAttribute("user", user);
		model.addAttribute("count", count);
		model.addAttribute("artistId", artistid);
		model.addAttribute("artist", artist);
		model.addAttribute("profileUrl", artist.getProfileUrl());
		model.addAttribute("productList", aservice.getPopularProductByCategory(artistid, category));
		return "carthometab";
	}

	@RequestMapping(value = "/product/details", method = RequestMethod.POST)
	@ResponseBody
	public String productDetail(@RequestParam(value = "productId") Long productId) throws Exception {
		String Result = "";
		Product product = aservice.getProductByID(productId);
		if (product != null) {
			Result = "{\"Name\":";
			Result += "\"" + product.getProductName() + "\",";
			Result += "\"Description\":\"" + product.getProductDes() + "\",";
			Result += "\"Price\":\"" + product.getProductPrice() + "\",";
			Result += "\"Url\":\"" + product.getProductUrl() + "\"";
			Result += "}";
		} else {
			Result = "Invalid";
		}
		return Result;
	}

	@GetMapping("/shop")
	public String shopLandingPage(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = uservice.findUserByUserId(userDetails.getUserId());

		List<Object[]> topAllProductsBySale = uservice.getTopAllProductsInPastWeekByOrderDetailsQuantity(8);
		List<Object[]> topMusicCollectionProductsBySale = uservice
				.getTopMusicCollectionProductsInPastWeekByOrderDetailsQuantity(4);
		List<Object[]> topMerchandiseProductsBySale = uservice
				.getTopMerchandiseProductsInPastWeekByOrderDetailsQuantity(4);
		List<Object[]> topClothingProductsBySaleRaw = uservice
				.getTopClothingProductsInPastWeekByOrderDetailsQuantity(4);

		Map<Product, Long> allProductsAndCountShop = new LinkedHashMap<Product, Long>(8, 0.75F, true);
		Map<Product, Long> musicCollectionProductsAndCountShop = new LinkedHashMap<Product, Long>(16, 0.75F, true);
		Map<Product, Long> merchandiseProductsAndCountShop = new LinkedHashMap<Product, Long>(16, 0.75F, true);
		Map<Product, Long> clothingProductsAndCountShop = new LinkedHashMap<Product, Long>(16, 0.75F, true);

		for (Object[] object : topAllProductsBySale) {
			allProductsAndCountShop.put((Product) object[0], (Long) object[1]);
		}

		for (Object[] object : topMusicCollectionProductsBySale) {
			musicCollectionProductsAndCountShop.put((Product) object[0], (Long) object[1]);
		}

		for (Object[] object : topMerchandiseProductsBySale) {
			merchandiseProductsAndCountShop.put((Product) object[0], (Long) object[1]);
		}

		for (Object[] object : topClothingProductsBySaleRaw) {
			clothingProductsAndCountShop.put((Product) object[0], (Long) object[1]);
		}

		Long count = uservice.getItemCountByUserID(userDetails.getUserId());

		model.addAttribute("count", count);
		model.addAttribute("profileUrl", user.getProfileUrl());

		model.addAttribute("allProductsAndCountShop", allProductsAndCountShop);
		model.addAttribute("musicCollectionProductsAndCountShop", musicCollectionProductsAndCountShop);
		model.addAttribute("merchandiseProductsAndCountShop", merchandiseProductsAndCountShop);
		model.addAttribute("clothingProductsAndCountShop", clothingProductsAndCountShop);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("count", count);
		model.addAttribute("user", user);
		model.addAttribute("shopLinkActive", true);

		return "shoplandingpage";
	}

	@GetMapping("/shop/allproducts")
	public String allProductsLandingPage(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = uservice.findUserByUserId(userDetails.getUserId());

		List<Object[]> topAllProductsBySale = uservice.getAllProducts();

		Long count = uservice.getItemCountByUserID(userDetails.getUserId());

		Map<Product, Long> allProductsAndCountShop = new LinkedHashMap<Product, Long>(16, 0.75F, true);
		for (Object[] object : topAllProductsBySale) {
			Product product = (Product) object[0];
			if (product.getProductQty() > 0) {
				allProductsAndCountShop.put((Product) object[0], (Long) object[1]);
			}
		}

		model.addAttribute("allProductsAndCountShop", allProductsAndCountShop);
		model.addAttribute("category", "allProducts");
		model.addAttribute("count", count);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("user", user);

		return "shopcategorylandingpage";
	}

	@GetMapping("/shop/musiccollections")
	public String musicCollectionsLandingPage(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = uservice.findUserByUserId(userDetails.getUserId());

		List<Object[]> topAllProductsBySale = uservice.getAllMusicCollections();

		Map<Product, Long> allProductsAndCountShop = new LinkedHashMap<Product, Long>(16, 0.75F, true);
		for (Object[] object : topAllProductsBySale) {
			Product product = (Product) object[0];
			if (product.getProductQty() > 0) {
				allProductsAndCountShop.put((Product) object[0], (Long) object[1]);
			}
		}

		Long count = uservice.getItemCountByUserID(userDetails.getUserId());

		model.addAttribute("allProductsAndCountShop", allProductsAndCountShop);
		model.addAttribute("category", "musicCollection");
		model.addAttribute("count", count);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("user", user);

		return "shopcategorylandingpage";
	}

	@GetMapping("/shop/merchandise")
	public String merchandiseLandingPage(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = uservice.findUserByUserId(userDetails.getUserId());

		List<Object[]> topAllProductsBySale = uservice.getAllMerchandise();

		Map<Product, Long> allProductsAndCountShop = new LinkedHashMap<Product, Long>(16, 0.75F, true);
		for (Object[] object : topAllProductsBySale) {
			Product product = (Product) object[0];
			if (product.getProductQty() > 0) {
				allProductsAndCountShop.put((Product) object[0], (Long) object[1]);
			}
		}

		Long count = uservice.getItemCountByUserID(userDetails.getUserId());

		model.addAttribute("allProductsAndCountShop", allProductsAndCountShop);
		model.addAttribute("category", "merchandise");
		model.addAttribute("count", count);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("user", user);

		return "shopcategorylandingpage";
	}

	@GetMapping("/shop/clothing")
	public String clothingLandingPage(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = uservice.findUserByUserId(userDetails.getUserId());

		List<Object[]> topAllProductsBySale = uservice.getAllClothing();

		Map<Product, Long> allProductsAndCountShop = new LinkedHashMap<Product, Long>(16, 0.75F, true);
		for (Object[] object : topAllProductsBySale) {
			Product product = (Product) object[0];
			if (product.getProductQty() > 0) {
				allProductsAndCountShop.put((Product) object[0], (Long) object[1]);
			}
		}

		Long count = uservice.getItemCountByUserID(userDetails.getUserId());

		model.addAttribute("allProductsAndCountShop", allProductsAndCountShop);
		model.addAttribute("category", "clothing");
		model.addAttribute("count", count);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("user", user);

		return "shopcategorylandingpage";
	}

	@GetMapping("/purchasehistory")
	public String purchaseHistory(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {

		User user = uservice.findUserByUserId(userDetails.getUserId());

		List<Orders> purchaseHistoriesraw = uservice.getPurchaseHistoryByUserId(user.getUserID());
		Map<Orders, Double> purchaseHistories = new LinkedHashMap<Orders, Double>();
		for (Orders purchaseHistory : purchaseHistoriesraw) {
			double totalPrice = 0;
			for (OrderDetails purchaseHistoryOrderDetails : purchaseHistory.getOrderDetails()) {
				totalPrice = totalPrice + purchaseHistoryOrderDetails.getQuantity()
						* purchaseHistoryOrderDetails.getProduct().getProductPrice();
			}
			purchaseHistories.put(purchaseHistory, totalPrice);
		}

		Long count = uservice.getItemCountByUserID(userDetails.getUserId());

		model.addAttribute("purchaseHistories", purchaseHistories);
		model.addAttribute("count", count);
		model.addAttribute("user", user);
		model.addAttribute("profileUrl", user.getProfileUrl());

		return "product/purchasehistory";
	}
}
