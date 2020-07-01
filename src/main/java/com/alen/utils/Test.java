package com.alen.utils;



import com.alen.entity.MerchantZtree;
import com.alen.service.SmartMerchantService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class Test {

	private static BeanFactory factory;
	static {
		//factory = new ClassPathXmlApplicationContext(new String[]{"spring/spring-beans.xml","spring/spring-jdbc.xml","spring/spring-redis.xml"});
		//factory = new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
		factory = new ClassPathXmlApplicationContext(new String[]{"classpath:/cache/shiro-ehcache.xml","classpath:spring/spring-*.xml"});
	}

	public static void main(String[] args) {
		//BookService service = factory.getBean(BookService.class);
		//System.out.println(service.saveCancelBook(8));
		//BlacklistService service = factory.getBean(BlacklistService.class);
		//service.acceptBlacklist();
		/*TradeNoUtils tn = factory.getBean(TradeNoUtils.class);
		System.err.println(tn.getPayTradeNo());*/
		//PayparkingOrderService service = factory.getBean(PayparkingOrderService.class);
		//service.savePayparkingOrder("2004262020518949E875000000DE", "202004271030300000001", "0000001", DateUtils.getDate("2020-04-27 10:30:30", "yyyy-MM-dd HH:mm:ss"), new BigDecimal(2.00));
		SmartMerchantService service = factory.getBean(SmartMerchantService.class);

		for (MerchantZtree m:service.getList()) {
			System.err.println(m);
		}
	}

}
