package ktgu.lab.coconut.web.test.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import ktgu.lab.coconut.web.domain.User;
import ktgu.lab.coconut.web.exception.DuplicateEmailException;
import ktgu.lab.coconut.web.exception.DuplicateNameException;
import ktgu.lab.coconut.web.service.UserService;
import ktgu.lab.coconut.web.test.common.TransactionalSpringContextTestsBase;
import ktgu.lab.coconut.web.util.HResult;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceTests extends TransactionalSpringContextTestsBase {

	@Autowired
	private UserService userService;

	@Test
	public final void testLoginSuccess() throws DuplicateEmailException, DuplicateNameException {
		User user = this.createNewUser();
		assertNotNull(user);

		// 用用户名登陆
		HResult hr = userService.login(user.getName(), user.getPassword());
		assertTrue(hr.equals(HResult.SUCCESS));

		// 用邮箱登陆
		hr = userService.login(user.getEmail(), user.getPassword());
		assertTrue(hr.equals(HResult.SUCCESS));

		// 用手机号码登陆
		hr = userService.login(user.getMobile(), user.getPassword());
		assertTrue(hr.equals(HResult.SUCCESS));
	}

	@Test
	public final void testLoginWithPasswordNotMatched() throws DuplicateEmailException, DuplicateNameException {
		User user = this.createNewUser();
		assertNotNull(user);

		HResult hr = userService.login(user.getName(), "incorrect_" + user.getPassword());
		assertEquals(hr, HResult.PASSWORD_NOT_MATCHED);
	}

	@Test
	public final void testLoginFailedWithIdentifierNotExist() throws DuplicateEmailException, DuplicateNameException {
		User user = this.createNewUser();
		assertNotNull(user);

		HResult hr = userService.login("incorrect_" + user.getName(), user.getPassword());
		assertEquals(hr, HResult.IDENTIFIER_NOT_EXITS);
	}


	/**
	 * 测试注册新用户
	 */
	@Test
	public void testRegister() throws DuplicateNameException, DuplicateEmailException, InterruptedException {
		User user = new User();
		user.setName("ktgu");
		user.setPassword("aaaaaa");
		user.setEmail("29283212@qq.com");
		userService.register(user);

		TimeUnit.SECONDS.sleep(5);
	}


	private User createNewUser() throws DuplicateEmailException, DuplicateNameException {
		User u = new User();
		u.setName("test1");
		u.setPassword("aaaaaa");
		u.setMobile("test1");
		u.setEmail("test1@qq.com");
		u.setCreateTime(new Date());
		u.setAvatar("");

		return userService.register(u);
	}
}
