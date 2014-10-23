package hibernateValidationTest;

import com.ptsisi.daily.User;
import com.ptsisi.daily.model.UserBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhaoding on 14-10-23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring*.xml")
public class HibernateValidationTest {

	private User user;

	/**
	 * id不能为null
	 * createdAt不能为null
	 * updatedAt不能为null
	 * username不能为空,0-32长度
	 * password不能为空,6-16长度
	 * email不能为空,email
	 * fullname不能为空,0-100长度
	 */
	@Before
	public void initUser() {
		user = new UserBean();
		user.setEmail("aaa");
		user.setPassword("111");
		user.setUsername(
				"ajlkfjdslkjflkjsalkfjsalajklfjlksajlkfsajlkfjlksajflkjsafsdfsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajlkfjdslkjflkjsalkfjsalajklfjlksajlkfsajlkfjlksajflkjsafsdfsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajlkfjdslkjflkjsalkfjsalajklfjlksajlkfsajlkfjlksajflkjsafsdfsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajlkfjdslkjflkjsalkfjsalajklfjlksajlkfsajlkfjlksajflkjsafsdfsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajlkfjdslkjflkjsalkfjsalajklfjlksajlkfsajlkfjlksajflkjsafsdfsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajlkfjdslkjflkjsalkfjsalajklfjlksajlkfsajlkfjlksajflkjsafsdfsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajlkfjdslkjflkjsalkfjsalajklfjlksajlkfsajlkfjlksajflkjsafsdfsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajlkfjdslkjflkjsalkfjsalajklfjlksajlkfsajlkfjlksajflkjsafsdfsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajlkfjdslkjflkjsalkfjsalajklfjlksajlkfsajlkfjlksajflkjsafsdfsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaajlkfjdslkjflkjsalkfjsalajklfjlksajlkfsajlkfjlksajflkjsafsdfsaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
	}

	@Test
	public void validate() {
		Validator validator = Validation.buildDefaultValidatorFactory()
				.getValidator();
		assertEquals(validator
				.validate(this.user).size(), 7);
		assertEquals(validator
				.validateProperty(user, "email").size(), 1);
	}
}
