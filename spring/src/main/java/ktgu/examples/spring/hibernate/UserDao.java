package ktgu.examples.spring.hibernate;

import ktgu.examples.spring.hibernate.domain.User;

import java.util.List;

/**
 * Created by ktgu on 15/8/20.
 */
public interface UserDao {
    List<User> findUsers();

    User createUser();

    void save(User user);
}
