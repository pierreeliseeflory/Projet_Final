package com.excilys.librarymanager.dao.implementation;

import java.time.LocalDate;
import java.util.List;

import com.excilys.librarymanager.exception.DaoException;
import com.excilys.librarymanager.model.Emprunt;
import com.excilys.librarymanager.model.Livre;
import com.excilys.librarymanager.model.Membre;

public class EmpruntDaoImp {
	private static LivreDaoImp instance;
	private LivreDaoImp() { }	
	public static LivreDaoImp getInstance() {
		if(instance == null) {
			instance = new LivreDaoImp();
		}
		return instance;
	}
	
//	private int id;
//	private Membre membre;
//	private Livre livre;
//	private LocalDate dateEmprunt;
//	private LocalDate dateRetour;
	
	private static final String CREATE_QUERY = "INSERT INTO Livre (titre, auteur, isbn) VALUES (?, ?, ?);";
	private static final String SELECT_ONE_QUERY = "SELECT * FROM Livre WHERE id= ? ;";
	private static final String SELECT_ALL_QUERY = "SELECT * FROM Livre;";
	private static final String UPDATE_QUERY = "UPDATE Livre SET titre=?, auteur=?, isbn=? WHERE id=?;";
	private static final String DELETE_QUERY = "DELETE FROM Livre WHERE id=?;";
	
	public List<Emprunt> getList() throws DaoException;
	public List<Emprunt> getListCurrent() throws DaoException;
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException;
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException;
	public Emprunt getById(int id) throws DaoException;
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException;
	public void update(Emprunt emprunt) throws DaoException;
	public int count() throws DaoException;
}
