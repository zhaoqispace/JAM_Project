package sg.edu.iss.jam.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sg.edu.iss.jam.model.OrderDetails;
import sg.edu.iss.jam.model.Orders;
import sg.edu.iss.jam.model.Payment;
import sg.edu.iss.jam.model.Product;
import sg.edu.iss.jam.model.ShoppingCart;
import sg.edu.iss.jam.model.ShoppingCartDetails;
import sg.edu.iss.jam.model.User;
import sg.edu.iss.jam.security.MyUserDetails;
import sg.edu.iss.jam.service.UserInterface;

@Controller
public class ShoppingCartController {

	@Autowired
	UserInterface uservice;

	@GetMapping("/shoppingcart")
	public String shoppingCart(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
		User user = uservice.findUserByUserId(userDetails.getUserId());
		ShoppingCart cartInfo = uservice.getShoppingCartByUserID(userDetails.getUserId());
		Map<ShoppingCartDetails, Long> detailsAndCountQty = new HashMap<ShoppingCartDetails, Long>();
		if (cartInfo.getCartDetails() != null) {
			for (ShoppingCartDetails cartdetail : cartInfo.getCartDetails()) {
				Long quantity = 0L;
				if (cartdetail.getQuantity() > cartdetail.getProduct().getProductQty()) {
					quantity = (long) (cartdetail.getQuantity() - cartdetail.getProduct().getProductQty());
					cartdetail.setQuantity(cartdetail.getProduct().getProductQty());
					uservice.saveCartDetails(cartdetail);
				}
				detailsAndCountQty.put(cartdetail, quantity);
			}
		}
		model.addAttribute("cartLinkActive", true);
		model.addAttribute("cartForm", detailsAndCountQty);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("user", user);
		model.addAttribute("totalAmount", cartInfo.getAmountTotal());
		model.addAttribute("totalQuantity", cartInfo.getQuantityTotal());
		Long count = uservice.getItemCountByUserID(user.getUserID());
		model.addAttribute("count", count);
		return "product/shoppingCart";
	}

	// POST: Update quantity for product in cart
	@RequestMapping(value = { "/shoppingcart" }, method = RequestMethod.POST)
	public String shoppingCartUpdateQty(HttpServletRequest request, Model model, //
			@ModelAttribute("cartForm") ShoppingCart cartForm, @AuthenticationPrincipal MyUserDetails userDetails) {
		ShoppingCart cartInfo = uservice.getShoppingCartByUserID(userDetails.getUserId());
		cartInfo.updateQuantity(cartForm);

		return "redirect:shoppingcart";
	}

	@RequestMapping({ "/shoppingCartRemoveProduct" })
	public String removeProductHandler(HttpServletRequest request, Model model, //
			@RequestParam(value = "id", defaultValue = "") Long id,
			@AuthenticationPrincipal MyUserDetails userDetails) {
		Product product = null;
		if (id != null) {
			product = uservice.findProduct(id);
		}
		if (product != null) {

			ShoppingCart cartInfo = uservice.getShoppingCartByUserID(userDetails.getUserId());
			uservice.removeCartDetails(product.getProductID(), cartInfo.getShoppingCartID());

		}

		return "redirect:shoppingcart";
	}

	@GetMapping("/checkout")
	public String checkOut(Model model, @AuthenticationPrincipal MyUserDetails userDetails) {
		Payment payment = new Payment();
		User user = uservice.findUserByUserId(userDetails.getUserId());
		Long count = uservice.getItemCountByUserID(user.getUserID());
		model.addAttribute("newPayment", payment);
		model.addAttribute("profileUrl", user.getProfileUrl());
		model.addAttribute("count", count);
		model.addAttribute("cartForm", uservice.getShoppingCartByUserID(userDetails.getUserId()));
		return "product/checkout";
	}

	@PostMapping("/placeorder")
	public String placeOrder(Model model, @Valid @ModelAttribute("payment") Payment payment,
			@AuthenticationPrincipal MyUserDetails userDetails) {
		User user = null;
		Orders neworder = null;
		user = uservice.findUserByUserId(userDetails.getUserId());
		// insert order depending on shoppingcart
		ShoppingCart cart = uservice.getShoppingCartByUserID(userDetails.getUserId());
		if (cart.getCartDetails() != null) {
			neworder = new Orders(LocalDate.now(), payment.getAddress(), user, null);
			uservice.saveOrder(neworder);

			List<OrderDetails> orderDetailList = new ArrayList<>();
			for (ShoppingCartDetails cardetail : cart.getCartDetails()) {
				OrderDetails newOrderDetail = new OrderDetails(cardetail.getQuantity(), cardetail.getProduct(),
						neworder);
				orderDetailList.add(newOrderDetail);
				Product product = cardetail.getProduct();
				if (product.getProductQty() > 0 && product.getProductQty() >= cardetail.getQuantity()) {
					product.setProductQty(product.getProductQty() - cardetail.getQuantity());
				}
				uservice.updateProduct(product);
				// delete cart detail
				uservice.deleteCartDetails(cardetail);
			}
			uservice.saveOrderDetailsList(orderDetailList);
		}
		// save payment
		payment.setUser(user);
		uservice.savePayement(payment);
		model.addAttribute("user", user);
		model.addAttribute("order", neworder);
		Long count = uservice.getItemCountByUserID(user.getUserID());
		model.addAttribute("count", count);
		model.addAttribute("profileUrl", user.getProfileUrl());
		return "product/orderconfirm";
	}

	// ajax call
	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
	@ResponseBody
	public String add(@RequestParam(value = "productId") Long productId,
			@AuthenticationPrincipal MyUserDetails userDetails) throws Exception {

		// long userID = (long) 2;
		String result = "";
		String status = "";
		ShoppingCartDetails carddetail = null;
		if (productId != null) {
			ShoppingCart cart = uservice.getShoppingCartByUserID(userDetails.getUserId());
			Product product = uservice.findProduct(productId);
			carddetail = uservice.getCartDetailByProductID(productId, cart.getShoppingCartID());
			if (carddetail != null) {
				if (carddetail.getQuantity() + 1 <= product.getProductQty()) {
					carddetail.setQuantity(carddetail.getQuantity() + 1);
					uservice.saveCartDetails(carddetail);
					status = "ok";
				} else {
					status = "error";
				}
			} else {
				carddetail = new ShoppingCartDetails(1, uservice.findProduct(productId),
						uservice.getShoppingCartByUserID(userDetails.getUserId()));
				uservice.saveCartDetails(carddetail);
				status = "ok";
			}
		}
		Long count = uservice.getItemCountByUserID(userDetails.getUserId());
		result = "{\"status\":";
		result += "\"" + status + "\",";
		result += "\"count\":\"" + count + "\"";
		result += "}";
		return result;
	}

	@RequestMapping(value = "/updatecartitemqty", method = RequestMethod.PUT)
	@ResponseBody
	public String updateItem(@RequestParam(value = "productId") Long productId,
			@RequestParam(value = "quantity") Integer quantity, @AuthenticationPrincipal MyUserDetails userDetails)
			throws Exception {
		String result = "";
		Long uncount = 0L;
		ShoppingCart cart = uservice.getShoppingCartByUserID(userDetails.getUserId());
		if (quantity <= 0) {
			uservice.removeCartDetails(productId, cart.getShoppingCartID());
		} else {
			ShoppingCartDetails carddetail = uservice.getCartDetailByProductID(productId, cart.getShoppingCartID());
			if (quantity > carddetail.getProduct().getProductQty()) {
				if (carddetail.getProduct().getProductQty() != 0) {
					carddetail.setQuantity(carddetail.getProduct().getProductQty());
					uncount = (long) (quantity - carddetail.getProduct().getProductQty());
				} else {
					uservice.removeCartDetails(productId, cart.getShoppingCartID());
				}
			} else {
				carddetail.setQuantity(quantity);
			}
			uservice.saveCartDetails(carddetail);
		}
		result = "{\"uncount\":";
		result += "\"" + uncount + "\"";
		result += "}";
		return result;

	}

}
