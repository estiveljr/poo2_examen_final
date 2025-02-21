package model.database;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import control.ZooService;
import model.entity.Animal;

class ZooServiceTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
    final void testExceptionsIsThrownlisterAnimaux() {
        // Test avec une connexion nulle
        ZooService zooService = new ZooService();
        
        // Vérifier qu'une exception est lancée avec des paramètres invalides
        assertThrows(IllegalArgumentException.class, () -> {
            // Simuler des paramètres invalides
            zooService.rechercherAnimal(-1);
        }, "Une IllegalArgumentException devrait être lancée avec un ID négatif");
    }
	
	@Test
	final void testExceptionsIsThrownModifierAnimalInvalide() {

        // Test avec une connexion nulle
        ZooService zooService = new ZooService();
        
        // Vérifier qu'une exception est lancée avec des paramètres invalides
        assertThrows(IllegalArgumentException.class, () -> {
            // Simuler des paramètres invalides
        	Animal animal = new Animal(-1,"aa","aa",-1,"aa");
            zooService.modifierAnimal(animal);
        }, "Une IllegalArgumentException devrait être lancée avec un ID négatif");
	}
	

}

// decrivez brevement comment le principe SOLID single responsability est apliqué dans le project
