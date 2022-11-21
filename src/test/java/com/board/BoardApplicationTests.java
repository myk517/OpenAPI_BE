package com.board;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class BoardApplicationTests {
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private SqlSessionFactory sessionFactory;
	
	@Test
	public void testByApplicationContext() {
		try {
			System.out.println("====================");
			System.out.println(context.getBean("sqlSessionFactory")); //DBConfiguration클래스에서 Bean으로 지정한 method명과 똑같은 명으로 호출하면 해당 Bean정보를 읽어온다.
			System.out.println("====================");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBySqlSessionFactory() {
		try {
			System.out.println("====================");
			System.out.println(sessionFactory.toString());
			System.out.println("====================");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
