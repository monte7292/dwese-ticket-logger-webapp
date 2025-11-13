package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.dao;

import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.Region;
import java.sql.SQLException;
import java.util.List;
public interface RegionDAO {
    List<Region> listAllRegions();
    void insertRegion(Region region);
    void updateRegion(Region region);
    void deleteRegion(int id);
    void deleteImage(int id);
    Region getRegionById(int id);
    boolean existsRegionByCode(String code);
    boolean existsRegionByCodeAndNotId(String code, int id);
}
