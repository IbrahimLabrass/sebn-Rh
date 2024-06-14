package com.sebn.spring.login.repository;

import com.sebn.spring.login.models.Entretien;
import com.sebn.spring.login.models.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository

public interface EntretienRepository extends JpaRepository<Entretien, Integer> {
    List<Entretien> findEntretienByDossierCandidature_IdDossier(int id);
}
