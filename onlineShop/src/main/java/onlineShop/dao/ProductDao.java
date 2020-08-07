package onlineShop.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import onlineShop.model.Product;
//dao： 和database交互的逻辑，算是model的一部分，一般后端的第一步都是从这里开始写

@Repository // 被其他class引用
public class ProductDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void addProduct(Product product) {
		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.save(product);
			session.getTransaction().commit(); //insert data into database
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	
	public void deleteProduct(int productId) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Product product =(Product)session.get(Product.class, productId);
			session.delete(product);
			session.getTransaction().commit(); // 对数据库进行操作
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	
	public void updateProduct(Product product) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(product);
			session.getTransaction().commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			if(session != null) {
				session.close();
			}
		}
	}
	public Product getProductById(int productId) {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			//session.beginTransaction(); 如果是读数据，才需要beginTransaction
			Product product = (Product)session.get(Product.class, productId);
			return product;
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			if(session != null) {
				session.close();
			}
		}
		return null;
	}
	
	public List<Product>  getAllProducts() {
		List<Product> list = new ArrayList<>();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			//session.beginTransaction();
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<Product> criteriaQuery = criteriaBuilder.createQuery(Product.class);
			Root<Product> root = criteriaQuery.from(Product.class);
			criteriaQuery.select(root);
			list = session.createQuery(criteriaQuery).getResultList();
			//session.getTransaction().commit(); 只有对数据库crud才需要这一句话
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}finally {
			if(session != null) {
				session.close();
			}
		}
		return list;
	}
}
