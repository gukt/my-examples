package ktgu.examples.spring.hibernate;

import ktgu.examples.spring.hibernate.domain.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by ktgu on 15/8/20.
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<User> findUsers() {
        return this.sessionFactory.getCurrentSession()
                .createQuery("from User")
                .list();
    }

    @Override
    public User createUser() {
        return null;
    }

    @Override
    public void save(User user) {

    }
}
