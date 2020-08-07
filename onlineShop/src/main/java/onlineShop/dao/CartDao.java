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
}
