package week7;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @description:
 * @author:
 * @create:
 * @other:
 **/

@Service
@RequiredArgsConstructor
public class BatchInsertService {
    private final JdbcTemplate jdbcTemplate;

    @Transactional(rollbackFor = Exception.class)
    public String insertData() {
        String sql = "insert into order_info (id,user_id,order_other_info_id,update_time,create_time) values (?,?,?,?,?)";
        int length = 1000000; //插入数据量
        long start = System.currentTimeMillis();
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1,i);
                preparedStatement.setLong(2,i);
                preparedStatement.setLong(3,i);
                preparedStatement.setDate(4, new Date(System.currentTimeMillis()));
                preparedStatement.setDate(5, new Date(System.currentTimeMillis()));
            }

            @Override
            public int getBatchSize() {
                return length ;
            }
        });
        System.out.println(System.currentTimeMillis() - start);
        return "操作成功!!";
    }

}
