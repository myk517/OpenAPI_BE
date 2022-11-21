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
			System.out.println(context.getBean("sqlSessionFactory")); //DBConfigurationŬ�������� Bean���� ������ method��� �Ȱ��� ������ ȣ���ϸ� �ش� Bean������ �о�´�.
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
