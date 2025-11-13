package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.repositories;
import org.springframework.data.jdbc.repository.query.Query;
import java.util.List;
import java.util.Optional;

import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {
    List<Provincia> findAll() ;

    void save();

    void deleteById(Long id);

    Optional<Provincia> findById(Long id);

    boolean existsProvinceByCode(String code);

    @Query("SELECT COUNT(r) > 0 FROM Provincia r WHERE r.code = :code AND r.id != :id")
    boolean existsProvinceByCodeAndNotId(@Param("code") String code, @Param("id") Long id);

    List<Provincia> listAllProvinces();
}
