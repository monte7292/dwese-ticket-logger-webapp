package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.dao;

import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.Provincia;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ProvinciaDAOImpl implements ProvinciaDAO {
    // Logger para registrar eventos importantes en el DAO
    private static final Logger logger = LoggerFactory.getLogger(ProvinciaDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Lista todas las provincias de la base de datos.
     * @return Lista de provincias
     */
    @Override
    public List<Provincia> listAllProvinces() {
        logger.info("Listando todas las provincias de la base de datos.");
        String query = "SELECT p FROM Provincia p";
        List<Provincia> provincias = entityManager.createQuery(query, Provincia.class).getResultList();
        logger.info("Recuperadas {} provincias de la base de datos.", provincias.size());
        return provincias;
    }

    /**
     * Inserta una nueva provincia en la base de datos.
     * @param provincia Provincia a insertar
     */
    @Override
    public void insertProvince(Provincia provincia) {
        logger.info("Insertando provincia con código: {} y nombre: {}", provincia.getCode(), provincia.getName());
        entityManager.persist(provincia);
        logger.info("Provincia insertada con ID: {}", provincia.getId());
    }

    /**
     * Actualiza una provincia existente en la base de datos.
     * @param provincia Provincia a actualizar
     */
    @Override
    public void updateProvince(Provincia provincia) {
        logger.info("Actualizando provincia con id: {}", provincia.getId());
        entityManager.merge(provincia);
        logger.info("Provincia actualizada con id: {}", provincia.getId());
    }

    /**
     * Elimina una provincia de la base de datos.
     * @param id ID de la provincia a eliminar
     */
    @Override
    public void deleteProvince(int id) {
        logger.info("Eliminando provincia con id: {}", id);
        Provincia provincia = entityManager.find(Provincia.class, id);
        if (provincia != null) {
            entityManager.remove(provincia);
            logger.info("Provincia eliminada con id: {}", id);
        } else {
            logger.warn("No se encontró la provincia con id: {}", id);
        }
    }

    /**
     * Obtiene una provincia por su ID.
     * @param id ID de la provincia
     * @return Provincia correspondiente al ID
     */
    @Override
    public Provincia getProvinceById(int id) {
        logger.info("Recuperando provincia por id: {}", id);
        Provincia provincia = entityManager.find(Provincia.class, id);
        if (provincia != null) {
            logger.info("Provincia recuperada: {} - {}", provincia.getCode(), provincia.getName());
        } else {
            logger.warn("No se encontró la provincia con id: {}", id);
        }
        return provincia;
    }

    /**
     * Verifica si una provincia con el código especificado ya existe en la base de datos.
     * @param code el código de la provincia a verificar.
     * @return true si una provincia con el código ya existe, false de lo contrario.
     */
    @Override
    public boolean existsProvinceByCode(String code) {
        logger.info("Verificando si existe una provincia con el código: {}", code);
        String query = "SELECT COUNT(p) FROM Provincia p WHERE UPPER(p.code) = :code";
        Long count = entityManager.createQuery(query, Long.class)
                .setParameter("code", code.toUpperCase())
                .getSingleResult();
        boolean exists = count != null && count > 0;
        logger.info("Provincia con código: {} existe: {}", code, exists);
        return exists;
    }

    /**
     * Verifica si una provincia con el código especificado ya existe en la base de datos,
     * excluyendo una provincia con un ID específico.
     * @param code el código de la provincia a verificar.
     * @param id el ID de la provincia a excluir de la verificación.
     * @return true si una provincia con el código ya existe (y no es la provincia con el ID dado),
     * false de lo contrario.
     */
    @Override
    public boolean existsProvinceByCodeAndNotId(String code, int id) {
        logger.info("Verificando si existe una provincia con el código: {} excluyendo id: {}", code, id);
        String query = "SELECT COUNT(p) FROM Provincia p WHERE UPPER(p.code) = :code AND p.id != :id";
        Long count = entityManager.createQuery(query, Long.class)
                .setParameter("code", code.toUpperCase())
                .setParameter("id", id)
                .getSingleResult();
        boolean exists = count != null && count > 0;
        logger.info("Provincia con código: {} existe excluyendo id {}: {}", code, id, exists);
        return exists;
    }
}
