package org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.controllers;

import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.Region;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.repositories.ProvinciaRepository;
import org.iesalixar.daw2.alejandromonterodeltoro.dwese_ticket_logger_webapp.entities.Provincia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/provinces")
public class ProvinciaController {

    private static final Logger logger = LoggerFactory.getLogger(ProvinciaController.class);

    // DAO para gestionar las operaciones de las regiones en la base de datos
    @Autowired
    private ProvinciaRepository provinciaRepository;

    @GetMapping
    public String listProvincias(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String sort, Model model)
    {
        logger.info("Solicitando la lista de todas las regiones..." + search);
        Pageable pageable = PageRequest.of(page - 1, 5, getSort(sort));
        Page<Provincia> provincias;
        int totalPages = 0;
        if (search != null && !search.isBlank()) {
            provincias = provinciaRepository.findByNameContainingIgnoreCase(search, pageable);
            totalPages = (int) Math.ceil((double) provinciaRepository.countByNameContainingIgnoreCase(search) / 5);
        } else {
            provincias = provinciaRepository.findAll(pageable);
            totalPages = (int) Math.ceil((double) provinciaRepository.count() / 5);
        }
        logger.info("Se han cargado {} provincias.", provincias.toList().size());
        model.addAttribute("listProvincias", provincias.toList()); // Pasar la lista de regiones al modelo
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", page);
        model.addAttribute("search", search);
        model.addAttribute("sort", sort);
        return "province"; // Nombre de la plantilla Thymeleaf a renderizar
    }

    @GetMapping("/new")
    public String showNewForm(Model model) {
        logger.info("Mostrando formulario para nueva región.");
        // Cambiado a 'province' para coincidir con la plantilla Thymeleaf
        model.addAttribute("province", new Provincia());
        return "province-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }

    @GetMapping("/edit")
    public String showEditForm(@RequestParam("id") Long id, Model model) {
        logger.info("Mostrando formulario de edición para la región con ID {}", id);
        Provincia provincia = null;
        Optional<Provincia> provinciaOpt = provinciaRepository.findById(id);
        if (provincia == null) {
            logger.warn("No se encontró la región con ID {}", id);
        }
        // Cambiado a 'province' para coincidir con la plantilla Thymeleaf
        model.addAttribute("province", provinciaOpt);

        return "province-form"; // Nombre de la plantilla Thymeleaf para el formulario
    }

    @PostMapping("/insert")
    public String insertProvincia(@ModelAttribute("province") Provincia provincia, RedirectAttributes redirectAttributes) {
        logger.info("Insertando nueva región con código {}", provincia.getCode());
        /*if (provinciaRepository.existsProvinceByCode(provincia.getCode())) {
            logger.warn("El código de la región {} ya existe.", provincia.getCode());
            redirectAttributes.addFlashAttribute("errorMessage", "El código de la provincia ya existe.");
            // Corregido: ruta de redirección debe ser /provinces/new
            return "redirect:/provinces/new";
        }*/
        provinciaRepository.save(provincia);
        logger.info("Región {} insertada con éxito.", provincia.getCode());

        return "redirect:/provinces"; // Redirigir a la lista de regiones
    }

    @PostMapping("/update")
    public String updateProvincia(@ModelAttribute("province") Provincia provincia, RedirectAttributes redirectAttributes) {
        logger.info("Actualizando provincia con ID {}", provincia.getId());
        /*if (provinciaRepository.existsProvinceByCodeAndNotId(provincia.getCode())) {
            logger.warn("El código de la región {} ya existe para otra región.", provincia.getCode());
            redirectAttributes.addFlashAttribute("errorMessage", "El código de la provincia ya existe para otra región.");
            return "redirect:/provinces/edit?id=" + provincia.getId();
        }*/
        provinciaRepository.save(provincia);
        logger.info("Provincia con ID {} actualizada con éxito.", provincia.getId());
        return "redirect:/provinces"; // Redirigir a la lista de regiones
    }

    @PostMapping("/delete")
    public String deleteProvincia(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        logger.info("Eliminando provincia con ID {}", id);
        provinciaRepository.deleteById(id);
        logger.info("Provincia con ID {} eliminada con éxito.", id);
        return "redirect:/provinces"; // Redirigir a la lista de regiones
    }

    private Sort getSort(String sort) {
        if (sort == null) {
            return Sort.by("id").ascending();
        }
        return switch (sort) {
            case "nameAsc" -> Sort.by("name").ascending();
            case "nameDesc" -> Sort.by("name").descending();
            case "codeAsc" -> Sort.by("code").ascending();
            case "codeDesc" -> Sort.by("code").descending();
            case "idDesc" -> Sort.by("id").descending();
            default -> Sort.by("id").ascending();
        };
    }
}