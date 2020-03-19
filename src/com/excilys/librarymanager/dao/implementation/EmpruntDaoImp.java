package com.excilys.librarymanager.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.dao.EmpruntDao;
import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.model.Subscription;
import com.excilys.librarymanager.persistence.ConnectionManager;

public class EmpruntDaoImp implements EmpruntDao {
	
	private static EmpruntDaoImp instance;
	
	private EmpruntDaoImp() {}
	
	public static EmpruntDaoImp getInstance() {
		if(instance == null) {
			instance = new EmpruntDaoImp();
		}
		return instance;
	}
	
//	private static final String SELECT_ALL_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY dateRetour DESC;";
	private static final String SELECT_ALL_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre ORDER BY id;";

	private static final String SELECT_ALL_QUERY_NOT_RETURNED = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL;";
	private static final String SELECT_ALL_QUERY_NOT_RETURNED_ONE_MEMBER = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND membre.id=?;";
	private static final String SELECT_ALL_QUERY_NOT_RETURNED_ONE_BOOK = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE dateRetour IS NULL AND livre.id=?;";
	
	private static final String SELECT_ONE_QUERY = "SELECT e.id AS id, idMembre, nom, prenom, adresse, email, telephone, abonnement, idLivre, titre, auteur, isbn, dateEmprunt, dateRetour FROM emprunt AS e INNER JOIN membre ON membre.id = e.idMembre INNER JOIN livre ON livre.id = e.idLivre WHERE e.id=?;";

	private static final String CREATE_QUERY = "INSERT INTO emprunt (idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?, ?, ?, ?);";
	private static final String UPDATE_QUERY = "UPDATE emprunt SET idMembre=?, idLivre=?, dateEmprunt=?, dateRetour=? WHERE id=?;";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM emprunt";
	
	@Override
	public List<Emprunt> getList() throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();

		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
				ResultSet res = statement.executeQuery();
				){ 
			while(res.next() ) {
				Emprunt emprunt = new Emprunt();
				
				emprunt.setId(res.getInt("id"));
				
				int idMembre = res.getInt("idMembre");
				String nom = res.getString("nom");
				String prenom = res.getString("prenom");
				String adresse = res.getString("adresse");
				String email = res.getString("email");
				String telephone = res.getString("telephone");
				Subscription subscription = Subscription.valueOf(res.getString("abonnement"));
				Membre membre = new Membre(idMembre, nom, prenom, adresse, email, telephone, subscription);
				emprunt.setMembre(membre);
				
				int idLivre = res.getInt("idLivre");
				String titre = res.getString("titre");
				String auteur = res.getString("auteur");
				String isbn = res.getString("isbn");
				Livre livre = new Livre (idLivre, titre, auteur, isbn);
				emprunt.setLivre(livre);
				
				LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
				emprunt.setDateEmprunt(dateEmprunt);
				if (res.getDate("dateRetour") != null) {
					LocalDate dateRetour = res.getDate("dateRetour").toLocalDate();
					emprunt.setDateRetour(dateRetour);
				}
				emprunts.add(emprunt);
			}
			System.out.println("GET all: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de tous les Emprunts.");
		}
		return emprunts;
	}
	
	@Override
	public List<Emprunt> getListCurrent() throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();

		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY_NOT_RETURNED);
				ResultSet res = statement.executeQuery();
				){ 
			while(res.next() ) {
				Emprunt emprunt = new Emprunt();
				
				emprunt.setId(res.getInt("id"));
				
				int idMembre = res.getInt("idMembre");
				String nom = res.getString("nom");
				String prenom = res.getString("prenom");
				String adresse = res.getString("adresse");
				String email = res.getString("email");
				String telephone = res.getString("telephone");
				Subscription subscription = Subscription.valueOf(res.getString("abonnement"));
				Membre membre = new Membre(idMembre, nom, prenom, adresse, email, telephone, subscription);
				emprunt.setMembre(membre);
				
				int idLivre = res.getInt("idLivre");
				String titre = res.getString("titre");
				String auteur = res.getString("auteur");
				String isbn = res.getString("isbn");
				Livre livre = new Livre (idLivre, titre, auteur, isbn);
				emprunt.setLivre(livre);
				
				LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
				emprunt.setDateEmprunt(dateEmprunt);
				if (res.getDate("dateRetour") != null) {
					LocalDate dateRetour = res.getDate("dateRetour").toLocalDate();
					emprunt.setDateRetour(dateRetour);
				}
				emprunts.add(emprunt);
			}
			System.out.println("GET all: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation de tous les Emprunts.");
		}
		return emprunts;
	}
	
	@Override
	public List<Emprunt> getListCurrentByMembre(int idMembreSearch) throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet res = null;

		try { 
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(SELECT_ALL_QUERY_NOT_RETURNED_ONE_MEMBER);
			statement.setInt(1, idMembreSearch);
			res = statement.executeQuery();
			while(res.next() ) {
				Emprunt emprunt = new Emprunt();
				
				emprunt.setId(res.getInt("id"));
				
				int idMembre = res.getInt("idMembre");
				String nom = res.getString("nom");
				String prenom = res.getString("prenom");
				String adresse = res.getString("adresse");
				String email = res.getString("email");
				String telephone = res.getString("telephone");
				Subscription subscription = Subscription.valueOf(res.getString("abonnement"));
				Membre membre = new Membre(idMembre, nom, prenom, adresse, email, telephone, subscription);
				emprunt.setMembre(membre);
				
				int idLivre = res.getInt("idLivre");
				String titre = res.getString("titre");
				String auteur = res.getString("auteur");
				String isbn = res.getString("isbn");
				Livre livre = new Livre (idLivre, titre, auteur, isbn);
				emprunt.setLivre(livre);
				
				LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
				emprunt.setDateEmprunt(dateEmprunt);
				if (res.getDate("dateRetour") != null) {
					LocalDate dateRetour = res.getDate("dateRetour").toLocalDate();
					emprunt.setDateRetour(dateRetour);
				}
				emprunts.add(emprunt);
			}
			System.out.println("GET all: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation des les Emprunts pour le membre");
		}
		return emprunts;
	}
	
	@Override
	public List<Emprunt> getListCurrentByLivre(int idLivreSearch) throws DaoException{
		List<Emprunt> emprunts = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet res = null;

		try { 
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(SELECT_ALL_QUERY_NOT_RETURNED_ONE_BOOK);
			statement.setInt(1, idLivreSearch);
			res = statement.executeQuery();
			while(res.next() ) {
				Emprunt emprunt = new Emprunt();
				
				emprunt.setId(res.getInt("id"));
				
				int idMembre = res.getInt("idMembre");
				String nom = res.getString("nom");
				String prenom = res.getString("prenom");
				String adresse = res.getString("adresse");
				String email = res.getString("email");
				String telephone = res.getString("telephone");
				Subscription subscription = Subscription.valueOf(res.getString("abonnement"));
				Membre membre = new Membre(idMembre, nom, prenom, adresse, email, telephone, subscription);
				emprunt.setMembre(membre);
				
				int idLivre = res.getInt("idLivre");
				String titre = res.getString("titre");
				String auteur = res.getString("auteur");
				String isbn = res.getString("isbn");
				Livre livre = new Livre (idLivre, titre, auteur, isbn);
				emprunt.setLivre(livre);
				
				LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
				emprunt.setDateEmprunt(dateEmprunt);
				if (res.getDate("dateRetour") != null) {
					LocalDate dateRetour = res.getDate("dateRetour").toLocalDate();
					emprunt.setDateRetour(dateRetour);
				}
				emprunts.add(emprunt);
			}
			System.out.println("GET all: " + emprunts);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation des les Emprunts pour le membre");
		}
		return emprunts;
	}
	
	@Override
	public Emprunt getById(int id) throws DaoException {
		Emprunt emprunt = new Emprunt();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet res = null;
	
		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(SELECT_ONE_QUERY);
			statement.setInt(1, id);
			res = statement.executeQuery();
			if (res.next()) {
				emprunt.setId(res.getInt("id"));
				
				int idMembre = res.getInt("idMembre");
				String nom = res.getString("nom");
				String prenom = res.getString("prenom");
				String adresse = res.getString("adresse");
				String email = res.getString("email");
				String telephone = res.getString("telephone");
				Subscription subscription = Subscription.valueOf(res.getString("abonnement"));
				Membre membre = new Membre(idMembre, nom, prenom, adresse, email, telephone, subscription);
				emprunt.setMembre(membre);
				
				int idLivre = res.getInt("idLivre");
				String titre = res.getString("titre");
				String auteur = res.getString("auteur");
				String isbn = res.getString("isbn");
				Livre livre = new Livre (idLivre, titre, auteur, isbn);
				emprunt.setLivre(livre);
				
				LocalDate dateEmprunt = res.getDate("dateEmprunt").toLocalDate();
				emprunt.setDateEmprunt(dateEmprunt);
				if (res.getDate("dateRetour") != null) {
					LocalDate dateRetour = res.getDate("dateRetour").toLocalDate();
					emprunt.setDateRetour(dateRetour);
				}
			}
			System.out.println("GET by ID: " + emprunt);
		} catch (SQLException e){
			throw new DaoException("Probleme dans la recuperation de l'emprunt");
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return emprunt;
	}
	
	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet res = null;
		int id = -1;
		
		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, idMembre);
			statement.setInt(2, idLivre);
			statement.setDate(3, java.sql.Date.valueOf(dateEmprunt.toString()));
			statement.setNull(4, java.sql.Types.DATE);
			statement.executeUpdate();
			res = statement.getGeneratedKeys();
			if (res.next()) {
				id = res.getInt(1);
				Emprunt emprunt = new Emprunt();
				emprunt = getById(id);
				System.out.println("CREATE: " + emprunt);
			} else {
				throw new DaoException("");				
			}
		} catch (SQLException e){
			throw new DaoException("Probleme dans la creation du emprunt");
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	@Override
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt, LocalDate dateRetour) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet res = null;
		int id = -1;
		
		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, idMembre);
			statement.setInt(2, idLivre);
			statement.setDate(3, java.sql.Date.valueOf(dateEmprunt.toString()));
			statement.setDate(4, java.sql.Date.valueOf(dateRetour.toString()));
			statement.executeUpdate();
			res = statement.getGeneratedKeys();
			if (res.next()) {
				id = res.getInt(1);
				Emprunt emprunt = new Emprunt();
				emprunt = getById(id);
				System.out.println("CREATE: " + emprunt);
			} else {
				throw new DaoException("");				
			}
		} catch (SQLException e){
			throw new DaoException("Probleme dans la creation du emprunt");
		} finally {
			try {
				res.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	@Override
	public void update(Emprunt emprunt) throws DaoException{
		Connection connection = null;
		PreparedStatement statement = null;	
		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(UPDATE_QUERY);
			statement.setInt(1, emprunt.getMembre().getId());
			statement.setInt(2, emprunt.getLivre().getId());
			statement.setDate(3, java.sql.Date.valueOf(emprunt.getDateEmprunt().toString()));
			statement.setDate(4, java.sql.Date.valueOf(emprunt.getDateRetour().toString()));
			statement.setInt(5, emprunt.getId());
			statement.executeUpdate();
			System.out.println("UPDATE: " + emprunt);
		} catch (SQLException e){
			throw new DaoException("Probleme dans la mise a jour de l'emprunt");
		} finally {
			try {
				statement.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
  };
	
    @Override
	public int count() throws DaoException {  
	  int nbEmprunts = 0;
	  try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(COUNT_QUERY);
				ResultSet res = statement.executeQuery();
				){ 
			while(res.next() ) {
				nbEmprunts = res.getInt("count");
		}
		System.out.println("COUNT: " + nbEmprunts);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors du comptage des emprunts.");
		}
		return nbEmprunts;
	};
	
	public static void main(String[] args) {
		System.out.println("======= Début des test =======");
		try {
			EmpruntDaoImp daoImp = getInstance();
			daoImp.getList();
			daoImp.getListCurrent();
			daoImp.getListCurrentByMembre(5);
			daoImp.getListCurrentByLivre(3);
			daoImp.count();
//			LocalDate dateEmprunt = LocalDate.of(2020, Month.FEBRUARY, 10);
//			daoImp.create(1, 1, dateEmprunt);
//			int id = daoImp.count();
//			Emprunt emprunt = daoImp.getById(73);
//			LocalDate dateRetour = LocalDate.now();
//			emprunt.setDateRetour(dateRetour);
//			daoImp.update(emprunt);
//			dateRetour = LocalDate.of(2020, Month.MARCH, 1);
//			daoImp.create(1, 1, dateEmprunt, dateRetour);
//			Emprunt emprunt = daoImp.getById(73);
		} catch (DaoException e) {
			System.out.println("C'est la merde.");
		}
	}
}
