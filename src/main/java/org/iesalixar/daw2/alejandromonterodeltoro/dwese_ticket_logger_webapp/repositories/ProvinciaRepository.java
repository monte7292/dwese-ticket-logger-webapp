package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.repositories;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.Region;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import java.util.List;
import java.util.Optional;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

    Page<Provincia> findAll(Pageable pageable);

    Page<Provincia> findByNameContainingIgnoreCase(String name, Pageable pageable);

    long countByNameContainingIgnoreCase(String name);

    @Query("SELECT COUNT(r) > 0 FROM Provincia r WHERE r.code = :code")
    boolean existsProvinceByCode(@Param("code") String code);

}
