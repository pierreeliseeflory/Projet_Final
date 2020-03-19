package com.excilys.librarymanager.dao.implementation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.model.Membre;
import com.excilys.librarymanager.dao.MembreDao;
import com.excilys.librarymanager.model.Subscription;
import com.excilys.librarymanager.persistence.ConnectionManager;

public class MembreDaoImp implements MembreDao{
	private static MembreDaoImp instance;
	private MembreDaoImp() { }	
	public static MembreDaoImp getInstance() {
		if(instance == null) {
			instance = new MembreDaoImp();
		}
		return instance;
	}
	
//	private int id;
//	private String nom;
//	private String prenom;
//	private String adresse;
//	private String email;
//	private String telephone;
//	private Subscription abonnement;
	
	private static final String CREATE_QUERY = "INSERT INTO Membre (nom, prenom, adresse, email, telephone, abonnement) VALUES (?, ?, ?);";
	private static final String SELECT_ONE_QUERY = "SELECT * FROM Membre WHERE id= ? ;";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM Membre;";
	private static final String UPDATE_QUERY = "UPDATE Membre SET nom=?, prenom=?, adresse=?, email=?, telephone=?, abonnement=? WHERE id=?;";
	private static final String DELETE_QUERY = "DELETE FROM Membre WHERE id=?;";
	
	@Override
	public List<Membre> getList() throws DaoException{
		List<Membre> membres = new ArrayList<>();
		
		try (
				Connection connection = ConnectionManager.getConnection();
				PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY);
				ResultSet res = statement.executeQuery();
				){ 
			while(res.next() ) {
				Membre l = new Membre(res.getInt("id"), res.getString("nom"), res.getString("prenom"), res.getString("adresse"), res.getString("email"), res.getString("telephone"), Subscription.valueOf(res.getString("abonnement")));
				membres.add(l);
			}
			System.out.println("GET: " + membres);
		} catch (SQLException e) {
			throw new DaoException("Probleme lors de la recuperation des Membres.");
		}
		return membres;
				
	}
	
	@Override
	public Membre getById(int id) throws DaoException {
		Membre membre = new Membre();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet res = null;
		
		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(SELECT_ONE_QUERY);
			statement.setInt(1, id);
			res = statement.executeQuery();
			if (res.next()) {
				membre.setId(res.getInt("id"));
				membre.setnom(res.getString("nom"));
				membre.setprenom(res.getString("prenom"));
				membre.setAdresse(res.getString("adresse"));
				membre.setEmail(res.getString("email"));
				membre.settelephone(res.getString("telephone"));
				membre.setabonnement(Subscription.valueOf(res.getString("abonnement")));
			}
			System.out.println("GET: " + membre);
		} catch (SQLException e){
			throw new DaoException("Probleme dans la recuperation du membre");
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
		return membre;
	}
	
	@Override
	public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet res = null;
		int id = -1;
		
		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, nom);
			statement.setString(2, prenom);
			statement.setString(3, adresse);
			statement.setString(4, email);
			statement.setString(5, telephone);
			statement.executeUpdate();
			res = statement.getGeneratedKeys();
			if (res.next()) {
				id = res.getInt(1);
			}
			Membre membre = new Membre();
			membre = getById(id);
			System.out.println("CREATE: " + membre);
		} catch (SQLException e){
			throw new DaoException("Probleme dans la creation du membre");
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
		return id;
	};
	
	@Override
	public void update(Membre membre) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;	
		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(UPDATE_QUERY);
			statement.setString(1, membre.getnom());
			statement.setString(2, membre.getprenom());
			statement.setString(3, membre.getAdresse());
			statement.setString(4, membre.getEmail());
			statement.setString(5, membre.gettelephone());
			statement.setString(6, membre.getabonnement().toString());
			statement.executeUpdate();
			System.out.println("UPDATE: " + membre);
		} catch (SQLException e){
			throw new DaoException("Probleme dans la mise a jour du membre");
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
	}
	
	@Override
	public void delete(int id) throws DaoException {
		Connection connection = null;
		PreparedStatement statement = null;	
		try {
			Membre membre = new Membre();
			membre = getById(id);
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(DELETE_QUERY);
			statement.setInt(1, membre.getId());
			statement.executeUpdate();
			statement.close();
			System.out.println("DELETE: " + membre);
		} catch (SQLException e){
			throw new DaoException("Probleme dans la suppression du membre");
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
	}
	
	@Override
	public int count() throws DaoException {
		List<Membre> stocks = getList();
		return stocks.size();
  };
	
	
}
