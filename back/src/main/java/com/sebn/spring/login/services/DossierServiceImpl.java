package com.sebn.spring.login.services;



import com.sebn.spring.login.models.*;
import com.sebn.spring.login.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;


@Service
public class DossierServiceImpl implements IDossierService {

    @Autowired
    DossierCandidatureRepository dossierCandidatureRepository;
    @Autowired
    CandidatRepository candidatRepository;

    @Override
    public List<DossierCandidature> findAll() {
        return dossierCandidatureRepository.findAll();
    }

    @Override
    public DossierCandidature getDossierCandidatureById(int id) {
        return dossierCandidatureRepository.findById(id).get();
    }

    @Override
    public DossierCandidature addDossierCandidature(DossierCandidature dc) {
        return dossierCandidatureRepository.save(dc);
    }

    @Override
    public void update(int id, DossierCandidature d) {
        DossierCandidature dossier = dossierCandidatureRepository.findById(id).get();
        dossier.setIntitule(d.getIntitule());
        dossier.setDateCreation(d.getDateCreation());
        dossier.setStatut(d.getStatut());
        dossierCandidatureRepository.saveAndFlush(dossier);
    }

    @Override
    public void updateState(int id, DossierCandidature d) {
        DossierCandidature dossier = dossierCandidatureRepository.findById(id).get();
        dossier.setStatut(d.getStatut());
        dossierCandidatureRepository.saveAndFlush(dossier);
    }

    @Override
    public long count() {
        return dossierCandidatureRepository.count();
    }

    @Override
    public List<DossierCandidature> getDossierByCandidat(int id) {
        return dossierCandidatureRepository.findDossierCandidatureByCandidat_IdCandidat(id);
    }


    @Override
    public void deleteDossierCandidature(int id) {
        dossierCandidatureRepository.deleteById(id);
    }

    @Override
    public List<DossierCandidature> getDossierCandidatureByManager(long id) {
        return dossierCandidatureRepository.findDossierCandidatureByUser_Id(id);
    }
}
