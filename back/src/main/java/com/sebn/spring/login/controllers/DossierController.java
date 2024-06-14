package com.sebn.spring.login.controllers;


import com.sebn.spring.login.models.*;
import com.sebn.spring.login.repository.*;
import com.sebn.spring.login.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/dossier")
public class DossierController {

    @Autowired
    IDossierService dossierService;
    @Autowired
    DossierCandidatureRepository CandidatRepository;


    @GetMapping("/getAllDossiers")
    public List<DossierCandidature> getAll() {
        return dossierService.findAll();
    }

    @PostMapping("/AddDossier")
    public DossierCandidature ajouterDossier(@RequestBody DossierCandidature d) {
        return dossierService.addDossierCandidature(d);
    }

    @DeleteMapping("/deleteDossier/{id}")
    public String deleteDossierById(@PathVariable("id") int id) {
        dossierService.deleteDossierCandidature(id);
        return "Deleted Successfully";
    }

    @GetMapping("/getdossier/{id}")
    @ResponseBody
    public DossierCandidature getDossierCandidatureById(@PathVariable("id") int id) {
        return dossierService.getDossierCandidatureById(id);

    }

    @PutMapping("/update/{id}")
    public void updateCandidat(@PathVariable int id, @RequestBody DossierCandidature d) {

        dossierService.update(id, d);
    }

    @GetMapping("/count")
    public long count() {
        return dossierService.count();
    }

    @GetMapping("/getDossierByManager/{id}")
    public List<DossierCandidature>  getDossier(@PathVariable long id) {
        return dossierService.getDossierCandidatureByManager(id);
    }

    @PutMapping("/updateState/{id}")
    public void updateState(@PathVariable int id, @RequestBody DossierCandidature d) {

        dossierService.updateState(id, d);
    }

    @GetMapping("/getDossierByCandidat/{id}")
    public List<DossierCandidature> getDossierByCandidat(@PathVariable int id) {
        return dossierService.getDossierByCandidat(id);
    }

}
