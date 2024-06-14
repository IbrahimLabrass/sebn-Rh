package com.sebn.spring.login.repository;


import com.sebn.spring.login.models.Candidat;
import com.sebn.spring.login.models.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;


@Repository
public interface CandidatRepository extends JpaRepository<Candidat, Integer> {
    List<Candidat> findByNomContainingIgnoreCaseAndPrenomContainingIgnoreCase(String nom, String prenom);

    Boolean existsCandidatByEmail(String email);


}
