package onlineShop.dao;

import java.io.IOException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.model.Cart;
import onlineShop.model.CartItem;

@Repository 
public class CartDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public Cart getCartById(int cartId) {
		Cart cart = null;
		//session的另外一种写法，这种方法使用于如果操作失败，不需要对session roll back，比如读请求
		try(Session session = sessionFactory.openSession()) {
			//session.beginTransaction(); 读请求不需要begin session
			cart = (Cart)session.get(Cart.class, cartId);
			//session.getTransaction().commit();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cart;
	}
	
	public Cart Validate(int cartId) throws IOException{
		Cart cart = getCartById(cartId);
		if(cart == null || cart.getCartItem().size() == 0) {
			throw new IOException(cartId + "");
		}
		update(cart);
		return cart;
	}
	
	private void update(Cart cart) {
		double total = getSalesOrderTotal(cart);
		cart.setTotalPrice(total);
		
		try(Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			session.saveOrUpdate(cart);
			session.getTransaction().commit(); // 为什么这里不关闭session里？
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} 
			//finally {
//
//		}
		
		
	}
	
	private double getSalesOrderTotal(Cart cart) {
		double sum = 0;
		List<CartItem> cartItems = cart.getCartItem();
		for(CartItem cartItem: cartItems) {
			sum += cartItem.getPrice();
		}
		return sum;
	}
}
