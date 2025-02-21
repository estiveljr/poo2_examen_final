package control;

import java.util.List;

import model.database.ZooDatabaseManager;
import model.entity.Animal;
import model.entity.Enclos;

public class ZooService {
    private ZooDatabaseManager dbManager;

    public ZooService() {
        this.dbManager = ZooDatabaseManager.getInstance();
    }

    public boolean ajouterAnimal(Animal animal) {
        return dbManager.addAnimal(animal);
    }
    
    public boolean modifierAnimal(Animal animal) {
    	if(animal.getId() < 0 ) {throw new IllegalArgumentException();};
        return dbManager.updateAnimal(animal);
    }

    // whats the problem?
    public Animal rechercherAnimal(int id) throws IllegalArgumentException{
    	if(id < 0 ) {throw new IllegalArgumentException();};
        return dbManager.getAnimalById(id);
    }

    public List<Animal> listerAnimaux() {
        return dbManager.getAllAnimals();
    }
    
    public boolean supprimerAnimal(Animal animal) {
    	return dbManager.deleteAnimal(animal);
    }
    
    public List<Enclos> listerEnclos() {
    	return dbManager.getAllEnclos();
    }
}