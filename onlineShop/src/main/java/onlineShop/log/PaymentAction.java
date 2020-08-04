package onlineShop.log;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component // new一个bean，自动 生成bean名字： paymentAction
public class PaymentAction {
	@Autowired //注入一个bean ： serverLogger
	private Logger logger;
	
	public void pay(BigDecimal payValue) {
		logger.log("pay begin, payValue is " + payValue);
		logger.log("pay end");
	}
}
