package service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import dto.Sms;

@Repository
public class SmsRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SmsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Sms sms) {
        String sql = "INSERT INTO sms (phone, athu, create_at) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, sms.getTo(), sms.getSmsConfirmNum(),Timestamp.valueOf(sms.getCreatedAt()));
    }

    public Sms findById(Long id) {
        String sql = "SELECT * FROM sms WHERE sms_seq = ?";
        return jdbcTemplate.queryForObject(sql, new SmsRowMapper(), id);
    }

    public List<Sms> findAll() {
        String sql = "SELECT * FROM sms";
        return jdbcTemplate.query(sql, new SmsRowMapper());
    }

    private static class SmsRowMapper implements RowMapper<Sms> {
        @Override
        public Sms mapRow(ResultSet rs, int rowNum) throws SQLException {
            Sms sms = new Sms();
            sms.setId(rs.getLong("sms_seq"));
            sms.setTo(rs.getString("phone"));
            sms.setSmsConfirmNum(rs.getString("athu"));
            sms.setCreatedAt(rs.getTimestamp("create_at").toLocalDateTime());
            return sms;
        }
    }
}
