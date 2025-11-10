package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.dao;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.Provincia;
import java.sql.SQLException;
import java.util.List;
public interface ProvinciaDAO {
    //Hacemos el listado de las entidades de provicna
    List<Provincia> listAllProvincia() throws SQLException;
    //Generamos del contructor Provincia
    void insertProvincia(Provincia region) throws SQLException;
    void updateProvincia(Provincia region) throws SQLException;
    void deleteProvincia(int id) throws SQLException;
    Provincia getProvinciaById(int id) throws SQLException;
    boolean existsProvinciaByCode(String code) throws SQLException;
    boolean existsProvinciaByCodeAndNotId(String code, long id) throws SQLException;
}
