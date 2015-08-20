package ktgu.examples.spring.hibernate;

import ktgu.examples.spring.hibernate.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ktgu on 15/8/20.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User createUser() {
        return userDao.createUser();
    }

    @Override
    public void save(User user) {
          userDao.save(user);
    }

    @Override
    public List<User> getUsers() {
        return userDao.findUsers();
    }
}
