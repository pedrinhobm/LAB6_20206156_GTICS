package com.example.clase6gtics.controller;
import com.example.clase6gtics.repository.*;
import com.example.clase6gtics.entity.*;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/pelicula")
public class PeliculaController {

    final PeliculaRepository peliculaRepository;


    public PeliculaController(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    @GetMapping(value = {"", "/"})
    public String listaPeliculas(Model model) {
        model.addAttribute("listaPelicula", peliculaRepository.findAll());
        return "pelicula/list";
    }

    @GetMapping("/new")
    public String nuevoPeliculaFrm(Model model, @ModelAttribute("pelicula") Pelicula pelicula) {
        model.addAttribute("listaPeliculas", peliculaRepository.findAll());
        return "pelicula/editFrm";
    }

    @PostMapping("/save")
    public String guardarPelicula(RedirectAttributes attr, Model model,
                                  @ModelAttribute("pelicula") @Valid Pelicula pelicula , BindingResult bindingResult ) {
        if (!bindingResult.hasErrors()) {

            if (pelicula.getTitulo().equals("coronado")) {
                model.addAttribute("msg", "Error al crear pelicula");
                model.addAttribute("listaPeliculas", peliculaRepository.findAll());
                return "pelicula/editFrm";
            } else {
                if (pelicula.getPeliculaid() == 0) {
                    attr.addFlashAttribute("msg", "Pelicula creada exitosamente");
                } else {
                    attr.addFlashAttribute("msg", "Pelicula actualizada exitosamente");
                }
                peliculaRepository.save(pelicula);
                return "redirect:/product";
            }

        } else {
            model.addAttribute("listaCategorias", peliculaRepository.findAll());
            return "pelicula/editFrm";
        }
    }

    @GetMapping("/edit")
    public String editarPelicula(@ModelAttribute("product") Pelicula pelicula,
                                      Model model, @RequestParam("peliculaId") int id) {

        Optional<Pelicula> optPelicula = peliculaRepository.findById(id);

        if (optPelicula.isPresent()) {
            model.addAttribute("pelicula", optPelicula.get());
            return "pelicula/editFrm";
        } else {
            return "redirect:/pelicula";
        }
    }


    }