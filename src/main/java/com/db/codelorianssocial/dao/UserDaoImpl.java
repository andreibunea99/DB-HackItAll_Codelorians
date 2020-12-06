package com.db.codelorianssocial.dao;

import com.db.codelorianssocial.entity.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao{

    private JdbcTemplate jdbcTemplate;

    public UserDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int save(User user) {
        System.out.println(user);
        int maxIndex = jdbcTemplate.queryForObject("SELECT MAX(id) FROM users", int.class);
        System.out.println(maxIndex);

        jdbcTemplate.execute("ALTER TABLE `users` AUTO_INCREMENT = " + maxIndex);
//        ResultSet rs = jdbcTemplate.executeQuery("SELECT MAX(PHONE) FROM complaints");

        String sql = "insert into users (username, email, discord, password) values (?, ?, ?, ?)";

        return jdbcTemplate.update(sql, user.getId(), user.getEmail(), user.getDiscord(), user.getPassword());
    }

    @Override
    public int update(User user) {
        System.out.println(user);
        String sql = "UPDATE users SET username=?, email=?, discord=?, "
                + "password=? WHERE id=?";

        System.out.println(user.getId());

        int res = jdbcTemplate.update(sql, user.getId(), user.getEmail(),
                user.getDiscord(), user.getPassword(), user.getId());

        List<User> contactList = list();
        for (User obj : contactList) {
            System.out.println(obj);
        }

        return res;

//        return jdbcTemplate.update(sql, contact.getName(), contact.getEmail(), contact.getAddress(), contact.getPhone(),
//                contact.getId());
    }

    @Override
    public User get(Integer id) {
        String sql = "select * from users where id = " + id;

        ResultSetExtractor<User> extractor = new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String discord = resultSet.getString("discord");
                    String password = resultSet.getString("password");

                    return new User(username, email, discord, password);
                }
                return null;
            }
        };
        return jdbcTemplate.query(sql, extractor);
    }

    public static String singleQuote(String str) {
        return (str != null ? "'" + str + "'" : null);
    }

    @Override
    public User get(String username) {
        String sql = "select * from users where username = " + singleQuote(username);

        ResultSetExtractor<User> extractor = new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    String email = resultSet.getString("email");
                    String discord = resultSet.getString("discord");
                    String password = resultSet.getString("password");

                    return new User(username, email, discord, password);
                }
                return null;
            }
        };
        return jdbcTemplate.query(sql, extractor);
    }

    @Override
    public int delete(Integer id) {
        String sql = "delete from users where id = " + id;

        return jdbcTemplate.update(sql);
    }

    @Override
    public List<User> list() {
        String sql = "select * from users";

        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                Integer id = resultSet.getInt("id");
                String name = resultSet.getString("username");
                String email = resultSet.getString("email");
                String discord = resultSet.getString("discord");
                String password = resultSet.getString("password");

                return new User(name, email, discord, password);
            }
        };
        return jdbcTemplate.query(sql, rowMapper);
    }
}