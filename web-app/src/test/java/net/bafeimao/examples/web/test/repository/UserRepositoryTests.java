package net.bafeimao.examples.web.test.repository;

import net.bafeimao.examples.web.domain.User;
import net.bafeimao.examples.web.repository.UserRepository;
import net.bafeimao.examples.web.test.common.TransactionalSpringContextTestsBase;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class UserRepositoryTests extends TransactionalSpringContextTestsBase {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        User u = this.createNewUser();
        System.out.println("Created successfully, user id:" + u.getId());
        assertNotNull(u.getId());
    }

    @Test
    public void testFindUserById() {
        User u = this.createNewUser();
        assertNotNull(u.getId());

        Long uid = u.getId();
        User u1 = userRepository.findById(uid);
        assertNotNull(u1);
    }

    @Test
    public void testFindAllUsers() {
        this.createNewUser();

        List<User> users = userRepository.findAll();
        Assert.assertNotNull(users);
        Assert.assertTrue(users.size() > 0);
    }

    @Test
    public void testFindUserByCriteria() {
        User u = this.createNewUser();

        List<User> users = userRepository.findAll("where name='" + u.getName() + "'");
        assertNotNull(users);
        assertTrue(users.size() == 1);
    }

    @Test
    public void testDeleteUserByEntity() {
        User user = this.createNewUser();
        assertNotNull(user);

        userRepository.delete(user);
    }

    @Test
    public void testUpdateUser() {
        User user = createNewUser();
        user.setName(user.getName() + "-updated");
        User user2 = userRepository.save(user);

        assertEquals(user, user2);
        assertTrue(user2.getName().contains("updated"));
    }

    @Test
    public void testCheckExistenceByName() {
        User user = createNewUser();

        assertTrue(userRepository.checkExistenceByName(user.getName()));
        assertFalse(userRepository.checkExistenceByName(user.getName() + "_not_found"));
    }

    @Test
    public void testCheckExistenceByEmail() {
        User user = createNewUser();
        assertTrue(userRepository.checkExistenceByEmail(user.getEmail()));
        assertFalse(userRepository.checkExistenceByEmail(user.getEmail() + "_not_found"));
    }

    @Test
    public void testDeleteUserThenAdd() {
        User user = this.createNewUser();
        assertNotNull(user);

        userRepository.delete(user);
        userRepository.save(user);
    }

//    // TODO 修正这个测试用例让其通过
//    @Test(expected = ConstraintViolationException.class)
//    public void testCreateUserWithConstraintViolation() throws Exception {
//        User u = new User();
//        u.setPassword("aaa");
//        u.setEmail("@qq.com");
//        userRepository.save(u);
//    }

    private User createNewUser() {
        User u = new User();
        u.setName("ktgu");
        u.setPassword("aaaaaa");
        u.setMobile("13611632335");
        u.setEmail("29283212@qq.com");
        u.setCreateTime(new Date());
        u.setAvatar("default");
        return userRepository.save(u);
    }
}
