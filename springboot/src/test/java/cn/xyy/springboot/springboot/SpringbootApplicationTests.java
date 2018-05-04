package cn.xyy.springboot.springboot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootApplicationTests {

	@Test
	public void contextLoads() {
		
		String testPassword = "xieyangyang";
		
		String salt = BCrypt.gensalt(16);
		
		System.out.println("===========颜值为=============" + salt);
		
		String hashpw = BCrypt.hashpw(testPassword, salt);
		
		System.out.println("==========加密后的密码为=========" + hashpw);
		
		boolean flag = BCrypt.checkpw(testPassword, hashpw);
		
		System.out.println("==========校验密码是否正确=========" + flag);
	}

}
