package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.repositories;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Long> {
    List<Region> findAll();

    void save();

    void deleteById(Long id);

    void deleteImage(Long id);

    Optional<Region> findById(Long id);

    boolean existsRegionByCode(String code);

    @Query("SELECT COUNT(r) > 0 FROM Region r WHERE r.code = :code AND r.id != :id")
    boolean existsRegionByCodeAndNotId(@Param("code") String code, @Param("id") Long id);
}
