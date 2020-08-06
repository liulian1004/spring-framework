package onlineShop.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.model.Authorities;
import onlineShop.model.Cart;
import onlineShop.model.Customer;
import onlineShop.model.User;

@Repository
public class CustomerDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addCustomer(Customer customer) {
		customer.getUser().setEnabled(true);// 默认是false，所以需要设置为true
		
		Authorities authorities = new Authorities();
		authorities.setAuthorities("ROLE_USER");
		authorities.setEmailId(customer.getUser().getEmailId());
		Cart cart = new Cart();
		cart.setCustomer(customer);
		customer.setCart(cart);
		//以上信息需要收到添加，其他信息都是从form（全端传过来）
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(authorities); //.save; .update; .delete==》 都是session自带的api
			session.save(customer);
			session.getTransaction().commit();//保存
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
				if(session != null) {
					session.close();
				}
			
		}
	}
		
		public Customer getCustomerByUserName(String userName) {
			User user = null;
			
			try (Session session = sessionFactory.openSession()){
				session.beginTransaction();
				//== select语句： 通过userName找到对应的custmoer信息
				CriteriaBuilder builder = session.getCriteriaBuilder();
				CriteriaQuery<User> criteraQuery = builder.createQuery(User.class);
				Root<User> root = criteraQuery.from(User.class);
				criteraQuery.select(root).where(builder.equal(root.get("emailId"), userName));
				user = session.createQuery(criteraQuery).getSingleResult();
				session.getTransaction().commit();
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
			if(user != null) {
				return user.getCustomer();
			}
			return null;
		
			
		}
		
	

}
