package model.entity;

public class Enclos {
	private int id;
	private String nom;
	private int capacite;
	private String typeHabitat;

	public Enclos(int id, String nom, int capacite, String typeHabitat) {
		super();
		this.id = id;
		this.nom = nom;
		this.capacite = capacite;
		this.typeHabitat = typeHabitat;
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

	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	public String getTypeHabitat() {
		return typeHabitat;
	}

	public void setTypeHabitat(String typeHabitat) {
		this.typeHabitat = typeHabitat;
	}

	@Override
	public String toString() {
		return String.format("Enclos{id=%d, nom='%s', capacite='%d', type_habitat='%s'}", this.id, this.nom,
				this.capacite, this.typeHabitat);
	}
}
