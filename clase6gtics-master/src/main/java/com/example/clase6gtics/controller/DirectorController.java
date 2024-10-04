package com.example.clase6gtics.controller;
import com.example.clase6gtics.repository.DirectorRepository;
import com.example.clase6gtics.entity.Director;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/director")
public class DirectorController {

    final DirectorRepository directorRepository;


    public DirectorController(DirectorRepository directorRepository) {
        this.directorRepository = directorRepository;
    }

    @GetMapping(value = {"", "/"})
    public String listaDirectores(Model model) {
        model.addAttribute("listaDirectores", directorRepository.findAll());
        return "director/list";
    }

    @GetMapping("/new")
    public String nuevoDirectorFrm(Model model, @ModelAttribute("director") Director director) {
        model.addAttribute("listaDirectores", directorRepository.findAll());
        return "director/editFrm";
    }

    @PostMapping("/save")
    public String guardarDirector(RedirectAttributes attr, Model model,
                                  @ModelAttribute("director") @Valid Director director , BindingResult bindingResult ) {
        if (!bindingResult.hasErrors()) {

            if (director.getNombre().equals("coronado")) {
                model.addAttribute("msg", "Error al crear director");
                model.addAttribute("listaDire", directorRepository.findAll());
                return "pelicula/editFrm";
            } else {
                if (director.getDirectorId() == 0) {
                    attr.addFlashAttribute("msg", "Director(a) creado(a) exitosamente");
                } else {
                    attr.addFlashAttribute("msg", "Director(a) actualizado(a) exitosamente");
                }
                directorRepository.save(director);
                return "redirect:/director";
            }

        } else {
            model.addAttribute("listaDirectores", directorRepository.findAll());
            return "director/editFrm";
        }
    }

    @GetMapping("/edit")
    public String editarDirector(@ModelAttribute("product") Director director,
                                      Model model, @RequestParam("id") int id) {

        Optional<Director> optDirector = directorRepository.findById(id);
        if (optDirector.isPresent()) {
            director = optDirector.get();
            model.addAttribute("pelicula", director);
            return "director/editFrm";
        } else {
            return "redirect:/director";
        }
    }


}