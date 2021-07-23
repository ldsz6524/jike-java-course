package week5;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author:
 * @create:
 * @other:
 **/
@Service
@RequiredArgsConstructor
public class MyJdbc {
    private final JdbcTemplate jdbcTemplate;

    public void list() {
        String sql = "select * from people";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql);
        System.err.println(maps);
    }

    public void insert() {
        String sql = "insert into people (id,age,`name`) values (?,?,?)";
        jdbcTemplate.update(sql, 1, 19, "John");
        jdbcTemplate.update(sql, 2, 18, "Mike");
    }

    public void update() {
        String sql = "update people set age=? where id=?";
        jdbcTemplate.update(sql, 21, 1);
    }

    public void delete() {
        String sql = "delete from people where id=?";
        jdbcTemplate.update(sql, 2);
    }

    public void deleteAll() {
        String sql = "delete from people";
        jdbcTemplate.update(sql);
    }


}
