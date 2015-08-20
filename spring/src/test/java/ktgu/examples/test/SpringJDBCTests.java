package ktgu.examples.test;

import ktgu.examples.spring.jdbc.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ktgu on 15/8/20.
 */
@ContextConfiguration(locations = {"classpath:context-jdbc.xml"})

public class SpringJDBCTests extends AbstractJUnit4SpringContextTests {
    static Logger logger = LoggerFactory.getLogger(SpringJDBCTests.class);

    @Autowired
    AbstractApplicationContext context;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;


    @Test
    public void testCount() {
        int result = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM ccn_users", Integer.class);

        logger.info("total users:{}", result);

    }

    @Test
    public void testQueryByParameter() {
        int result = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM ccn_users where name=?", Integer.class, "ktgu");

        logger.info("total users:{}", result);
    }

    @Test
    public void testQueryForString() {
        String mobile = jdbcTemplate.queryForObject(
                "SELECT mobile FROM ccn_users where name=?", String.class, new String[]{"ktgu"});

        logger.info("Mobile:{}", mobile);
    }


    @Test
    public void testWrapDomainObject() {
        User user = jdbcTemplate.queryForObject(
                "SELECT * FROM ccn_users ", new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User u = new User();
                        u.setName(rs.getString("name"));
                        u.setPassword(rs.getString("password"));
                        return u;
                    }
                });

        logger.info("user object:{}", user);
    }

    @Test
    public void testQueryMultiDomainObjects() {
        // 和上面一样，将queryObject替换成query方法即可

        List<User> users = jdbcTemplate.query(
                "SELECT * FROM ccn_users", new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                        User u = new User();
                        u.setName(rs.getString("name"));
                        u.setPassword(rs.getString("password"));
                        return u;
                    }
                });

        logger.info("user objects:{}", users);
    }

    @Test
    public void testSimpleJdbcInsert() {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("ccn_users");

        Map<String, Object> parameters = new HashMap<String, Object>(3);
        parameters.put("name", "longfei");
        parameters.put("password", "aaa");
        simpleJdbcInsert.execute(parameters);

    }

    @Test
    public void testSimpleJdbcInsertAndReturnKey() {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("ccn_users")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<String, Object>(3);
        parameters.put("name", "longfei");
        parameters.put("password", "aaa");

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        System.out.println(newId.longValue());

        // 这种方式也可以，还可以取得多个key
        KeyHolder keyHolder = simpleJdbcInsert.executeAndReturnKeyHolder(parameters);
        System.out.println(keyHolder.getKey().longValue());
    }

    // 上面有几个测试都是查询的时候写内部包装逻辑，这将引起多出写重复代码，所以可以用下面的方法定义一个Mapper类，可供多处使用
    private static final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User u = new User();
            u.setName(rs.getString("name"));
            u.setPassword(rs.getString("password"));
            return u;
        }
    }


}
