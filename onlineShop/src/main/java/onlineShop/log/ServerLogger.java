package onlineShop.log;

import org.springframework.stereotype.Component;

@Component(value = "serverLogger") //@Component: 这是告诉spring ServerLogger是bean，需要Spring放到container里（专门放bean的container）； value = 自定义名字
public class ServerLogger implements Logger{

	@Override
	public void log(String info) {

		System.out.println("server log = " + info);
		
	}
	

}
