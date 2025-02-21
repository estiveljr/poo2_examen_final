package model.entity;

public class Animal {
	private int id;
	private String nom;
	private String espece;
	private int age;
	private String regimeAlimentaire;

	public Animal(int id, String nom, String espece, int age, String regimeAlimentaire) {
		super();
		this.id = id;
		this.nom = nom;
		this.espece = espece;
		this.age = age;
		this.regimeAlimentaire = regimeAlimentaire;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEspece() {
		return espece;
	}

	public void setEspece(String espece) {
		this.espece = espece;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getRegimeAlimentaire() {
		return regimeAlimentaire;
	}

	public void setRegimeAlimentaire(String regimeAlimentaire) {
		this.regimeAlimentaire = regimeAlimentaire;
	}

	@Override
	public String toString() {
		return String.format("Animal{id=%d, nome='%s', espece='%s', age='%d', regime_alimentaire='%s'}", 
				this.id, this.nom, this.espece, this.age, this.regimeAlimentaire);
	}
}