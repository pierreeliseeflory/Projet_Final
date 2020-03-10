package com.excilys.librarymanager.model;

import java.time.LocalDate;

public class Emprunt {
	private int id;
	private Membre membre;
	private Livre livre;
	private LocalDate dateEmprunt;
	private LocalDate dateRetour;
	public Emprunt(int id, Membre membre, Livre livre, LocalDate dateEmprunt, LocalDate dateRetour) {
		super();
		this.id = id;
		this.membre = membre;
		this.livre = livre;
		this.dateEmprunt = dateEmprunt;
		this.dateRetour = dateRetour;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Membre getMembre() {
		return membre;
	}
	public void setMembre(Membre membre) {
		this.membre = membre;
	}
	public Livre getLivre() {
		return livre;
	}
	public void setLivre(Livre livre) {
		this.livre = livre;
	}
	public LocalDate getDateEmprunt() {
		return dateEmprunt;
	}
	public void setDateEmprunt(LocalDate dateEmprunt) {
		this.dateEmprunt = dateEmprunt;
	}
	public LocalDate getDateRetour() {
		return dateRetour;
	}
	public void setDateRetour(LocalDate dateRetour) {
		this.dateRetour = dateRetour;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dateEmprunt == null) ? 0 : dateEmprunt.hashCode());
		result = prime * result + ((dateRetour == null) ? 0 : dateRetour.hashCode());
		result = prime * result + id;
		result = prime * result + ((livre == null) ? 0 : livre.hashCode());
		result = prime * result + ((membre == null) ? 0 : membre.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Emprunt other = (Emprunt) obj;
		if (dateEmprunt == null) {
			if (other.dateEmprunt != null)
				return false;
		} else if (!dateEmprunt.equals(other.dateEmprunt))
			return false;
		if (dateRetour == null) {
			if (other.dateRetour != null)
				return false;
		} else if (!dateRetour.equals(other.dateRetour))
			return false;
		if (id != other.id)
			return false;
		if (livre == null) {
			if (other.livre != null)
				return false;
		} else if (!livre.equals(other.livre))
			return false;
		if (membre == null) {
			if (other.membre != null)
				return false;
		} else if (!membre.equals(other.membre))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Emprunt [id=" + id + ", membre=" + membre + ", livre=" + livre + ", dateEmprunt=" + dateEmprunt
				+ ", dateRetour=" + dateRetour + "]";
	}
	
	
}
