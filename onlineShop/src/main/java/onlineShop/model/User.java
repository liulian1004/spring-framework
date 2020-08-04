package onlineShop.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter //可以用lombok的annotation来声明setter/getter
@Getter
@Entity
@Table(name = "users")
public class User implements Serializable {
	
	private static final long serialVersionUID = 2681531852204068105L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String emailId;
	private String password;
	private boolean enabled;

	@OneToOne(mappedBy = "user")
	private Customer customer;


}
