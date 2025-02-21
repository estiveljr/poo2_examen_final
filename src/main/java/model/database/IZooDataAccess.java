package model.database;

import java.util.List;

import model.entity.Animal;
import model.entity.Enclos;

public interface IZooDataAccess {
	boolean addAnimal(Animal animal);

	Animal getAnimalById(int id);

	List<Animal> getAllAnimals();

	boolean updateAnimal(Animal animal);

	boolean deleteAnimal(Animal animal);

	List<Enclos> getAllEnclos();

}
