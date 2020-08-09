package onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import onlineShop.dao.CartItemDao;
import onlineShop.model.Cart;
import onlineShop.model.CartItem;

@Service
public class CartItemService {
	
	@Autowired
	private CartItemDao cartItemdao;
	
	public void addCartItem(CartItem cartItem) {
		cartItemdao.addCartItem(cartItem);
	}
	
	public void removeCartItem(int cartItemId) {
		cartItemdao.removeCartItem(cartItemId);
	}
	
	public void removeAllCartItems(Cart cart) {
		cartItemdao.removeAllCartItems(cart);
	}

}
