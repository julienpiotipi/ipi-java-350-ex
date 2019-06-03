package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeRepositoryTest {

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    public void testFindLastMatriculeNull(){
        //Given

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertEquals(null,lastMatricule);
    }

    @Test
    public void testFindByLastMatriculeSingle(){
        //Given
        Employe employe = new Employe("Doe","John","T12345", LocalDate.now(), Entreprise.SALAIRE_BASE,1,1.0);
        employeRepository.save(employe);

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertEquals("12345",lastMatricule);
    }

    @Test
    public void testFindByLastMatriculeMultiple(){
        //Given
        employeRepository.save(new Employe("Doe","John","T12345", LocalDate.now(), Entreprise.SALAIRE_BASE,1,1.0));
        employeRepository.save(new Employe("Doe","Joe","M40256", LocalDate.now(), Entreprise.SALAIRE_BASE,1,1.0));
        employeRepository.save(new Employe("Doe","Jul","C563245", LocalDate.now(), Entreprise.SALAIRE_BASE,1,1.0));

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertEquals("40256",lastMatricule);
    }

}