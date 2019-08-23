package top.ok.fine.pomegranate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.ok.fine.pomegranate.domain.User;
import top.ok.fine.pomegranate.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PomegranateApplicationTests {


	@Autowired
	private UserRepository userRepository;

	@Test
	public void contextLoads() {

		userRepository.save(new User("1","AAA","1234"));
	}

}
