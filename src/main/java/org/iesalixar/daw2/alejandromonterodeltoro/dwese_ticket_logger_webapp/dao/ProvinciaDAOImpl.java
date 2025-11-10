package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.dao;

import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.Provincia;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Repository
public class ProvinciaDAOImpl implements ProvinciaDAO {

    // Logger para registrar eventos importantes en el DAO
    private static final Logger logger = LoggerFactory.getLogger(ProvinciaDAOImpl.class);

    private final JdbcTemplate jdbcTemplate;

    // Inyección de JdbcTemplate
    public ProvinciaDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Provincia> listAllProvincia() {
        logger.info("Listing all provinces from the database.");
        String sql = "SELECT * FROM provinces";
        List<Provincia> provinces = jdbcTemplate.query(sql, new
                BeanPropertyRowMapper<>(Provincia.class));
        logger.info("Retrieved {} province from the database.", provinces.size());
        return provinces;
    }

    @Override
    public void insertProvincia(Provincia province) {
        logger.info("Inserting province with code: {} and name: {}", province.getCode(), province.getName());
        String sql = "INSERT INTO provinces (code, name) VALUES (?, ?)";
        int rowsAffected = jdbcTemplate.update(sql, province.getCode(), province.getName());
        logger.info("Inserted province. Rows affected: {}", rowsAffected);
    }

    @Override
    public void updateProvincia(Provincia province) {
        logger.info("Updating province with id: {}", province.getId());
        String sql = "UPDATE provinces SET code = ?, name = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, province.getCode(), province.getName(), province.getId());
        logger.info("Updated province. Rows affected: {}", rowsAffected);
    }

    @Override
    public void deleteProvincia(int id) {
        logger.info("Deleting province with id: {}", id);
        String sql = "DELETE FROM provinces WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        logger.info("Deleted province. Rows affected: {}", rowsAffected);
    }

    @Override
    public Provincia getProvinciaById(int id) {
        logger.info("Retrieving province by id: {}", id);
        String sql = "SELECT * FROM provinces WHERE id = ?";
        try {
            Provincia province = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Provincia.class), id);
            logger.info("Provincia retrieved: {} - {}", province.getCode(), province.getName());
            return province;
        } catch (Exception e) {
            logger.warn("No province found with id: {}", id);
            return null;
        }
    }

    @Override
    public boolean existsProvinciaByCode(String code) {
        logger.info("Checking if province with code: {} exists", code);
        String sql = "SELECT COUNT(*) FROM provinces WHERE UPPER(code) = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, code.toUpperCase());
        boolean exists = count != null && count > 0;
        logger.info("Provinces with code: {} exists: {}", code, exists);
        return exists;
    }

    @Override
    public boolean existsProvinciaByCodeAndNotId(String code, long id) {
        logger.info("Checking if province with code: {} exists excluding id: {}", code, id);
        String sql = "SELECT COUNT(*) FROM provinces WHERE UPPER(code) = ? AND id != ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, code.toUpperCase(), id);
        boolean exists = count != null && count > 0;
        logger.info("Provinces with code: {} exists excluding id {}: {}", code, id, exists);
        return exists;
    }
}

