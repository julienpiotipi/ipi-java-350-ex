package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class EmployeServiceIntegrationTest {

    @Autowired
    private EmployeRepository employeRepository;

    @Autowired
    private EmployeService employeService;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    public void testIntegrationEmbaucheEmploye() throws Exception{
        //Given
        employeRepository.save(new Employe("Doe","John","T12345", LocalDate.now(), Entreprise.SALAIRE_BASE,1,1.0));
        String nom = "Doe";
        String prenom = "John";
        Poste poste = Poste.TECHNICIEN;
        NiveauEtude niveauEtude = NiveauEtude.BTS_IUT;
        Double tempsPartiel = 1.0;

        //When
        employeService.embaucheEmploye(nom,prenom,poste,niveauEtude,tempsPartiel);

        //Then
        Employe e = employeRepository.findByMatricule("T12345");
        Assertions.assertEquals("T00001",e.getMatricule());
        Assertions.assertEquals(nom,e.getNom());
        Assertions.assertEquals(prenom,e.getPrenom());
        Assertions.assertEquals(LocalDate.now(),e.getDateEmbauche());
        Assertions.assertEquals(Entreprise.PERFORMANCE_BASE,e.getPerformance());
        // 1521,22 * 1,2 * 1.0 = 1825,46
        Assertions.assertEquals(1825.46,(double)e.getSalaire());
        Assertions.assertEquals(tempsPartiel,e.getTempsPartiel());
    }

    @Test
    public void testPerformanceCommercial() throws Exception {
        //Given
        employeRepository.save(new Employe("Doe", "John", "C12346", LocalDate.now(), Entreprise.SALAIRE_BASE, 10, 1.0));

        // When
        Employe employe = employeRepository.findByMatricule("C12346");
        employeService.calculPerformanceCommercial(employe.getMatricule(), new Long(15000), new Long(45300));

        // Then
        employe = employeRepository.findByMatricule("C12346");
        // le calcul de la performance correspond à 10 + 4 + 1
        Assertions.assertEquals(15, (int)employe.getPerformance());
    }
}