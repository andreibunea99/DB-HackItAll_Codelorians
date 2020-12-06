package com.db.codelorianssocial.dao;

import com.db.codelorianssocial.entity.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SecondChatDaoImpl implements SecondChatDao{

    private JdbcTemplate jdbcTemplate;

    public SecondChatDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int save(Message message) {
        System.out.println(message);

//        jdbcTemplate.execute("ALTER TABLE `gameRoom2Chat` AUTO_INCREMENT = " + maxIndex);
//        ResultSet rs = jdbcTemplate.executeQuery("SELECT MAX(PHONE) FROM complaints");

        String sql = "insert into gameRoom2Chat (username, message) values (?, ?)";

        return jdbcTemplate.update(sql, message.getUsername(), message.getMessage());
    }


    @Override
    public List<Message> list() {
        String sql = "select * from gameRoom2Chat";

        RowMapper<Message> rowMapper = new RowMapper<Message>() {
            @Override
            public Message mapRow(ResultSet resultSet, int i) throws SQLException {
                String username = resultSet.getString("username");
                String message = resultSet.getString("message");

                return new Message(username, message);
            }
        };
        return jdbcTemplate.query(sql, rowMapper);
    }
}