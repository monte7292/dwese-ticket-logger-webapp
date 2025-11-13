package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.dao;

import java.sql.SQLException;
import java.util.List;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.Provincia;
public interface ProvinciaDAO {
    List<Provincia> listAllProvinces() ;
    void insertProvince(Provincia province);
    void updateProvince(Provincia province);
    void deleteProvince(int id);
    Provincia getProvinceById(int id);
    boolean existsProvinceByCode(String code);
    boolean existsProvinceByCodeAndNotId(String code, int id);
}
