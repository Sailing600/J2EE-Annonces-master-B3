package fr.epsi.myEpsi.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import fr.epsi.myEpsi.beans.Annonce;
import fr.epsi.myEpsi.beans.Status;
import fr.epsi.myEpsi.beans.User;
import fr.epsi.myEpsi.service.UserService;
import utils.DbUtils;

public class AnnonceDao implements IAnnonceDao {

	DbUtils db;
	UserService userService;
	
	public AnnonceDao(){
		db = new DbUtils();
		userService = new UserService();
	}
	@Override
	public List<Annonce> getListOfAnnonces(User user) {
		Connection cn = db.getConnection();
		ResultSet resultats = null;

		ArrayList annonces = new ArrayList<Annonce>();
		try {
			PreparedStatement ps;
			ps = cn.prepareStatement("SELECT * FROM ANNONCES");
			if(!user.getAdministrator()){
				ps = cn.prepareStatement("SELECT * FROM ANNONCES WHERE USER_ID = ? OR STATUS = ? AND STATUS != ?");
				ps.setString(1, user.getId());
				ps.setInt(2, Status.PUBLIC.ordinal());
				ps.setInt(3, Status.ARCHIVED.ordinal());
			}	
			resultats = ps.executeQuery();
			    while (resultats.next()) {
			    	Annonce annonce = new Annonce();
			    	annonce.setId(resultats.getLong(1));
			    	annonce.setTitle(resultats.getString(2));
			    	annonce.setContent(resultats.getString(3));
			    	annonce.setAuthor(userService.getUserById(resultats.getString(4)));
			    	annonce.setCreationDate(resultats.getTimestamp(5));
			    	annonce.setUpdateDate(resultats.getTimestamp(6));
			    	annonce.setStatus(Status.fromOrdinal(resultats.getInt(7)));
			    	annonces.add(annonce);
			     }	   

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return annonces;
	}

	@Override
	public Annonce getAnnonces(Long id) {
		Connection cn = db.getConnection();
		ResultSet resultats = null;
		
		try {
			PreparedStatement ps = cn.prepareStatement("SELECT * FROM AnnonceS WHERE ID = ?");
			ps.setLong(1, id);
			resultats = ps.executeQuery();
			    while (resultats.next()) {
			    	Annonce Annonce = new Annonce();
			    	Annonce.setId(resultats.getLong(1));
			    	Annonce.setTitle(resultats.getString(2));
			    	Annonce.setContent(resultats.getString(3));
			    	Annonce.setAuthor(userService.getUserById(resultats.getString(4)));
			    	Annonce.setCreationDate(resultats.getTimestamp(5));
			    	Annonce.setUpdateDate(resultats.getTimestamp(6));
			    	Annonce.setStatus(Status.fromOrdinal(resultats.getInt(7)));
			    	return Annonce;
			     }	   

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	@Override
	public void addAnnonces(Annonce Annonce) {

		long nextId = 0;
		ResultSet resultats = null;
		Connection cn = db.getConnection();	
		try {
			String sqlIdentifier = "select MAX(ID) from AnnonceS";
			PreparedStatement pst = cn.prepareStatement(sqlIdentifier);
			resultats = pst.executeQuery();
			while (resultats.next()) {
				nextId = resultats.getLong(1) + 1;
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		 
		try {
			PreparedStatement ps = cn.prepareStatement("INSERT INTO AnnonceS(ID, TITLE, CONTENT, USER_ID, CREATION_DATE, UPDATE_DATE, STATUS) VALUES (?,?,?,?,?,?,?)");
			ps.setLong(1, nextId);
			ps.setString(2, Annonce.getTitle());
			ps.setString(3, Annonce.getContent());
			ps.setString(4, Annonce.getAuthor().getId());
			ps.setTimestamp(5, Annonce.getCreationDate());
			ps.setTimestamp(6, Annonce.getUpdateDate());
			ps.setInt(7, Annonce.getStatus().ordinal());
			ps.execute();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void updateAnnoncesStatus(Annonce Annonce, int status) {
		Connection cn = db.getConnection();		
		try {
			PreparedStatement ps = cn.prepareStatement("UPDATE Annonces SET status = ? WHERE id = ?");
			ps.setInt(1, status);
			ps.setLong(2, Annonce.getId());
			ps.execute();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
	}

	@Override
	public void deleteAnnonces(Annonce Annonce) {
		Connection cn = db.getConnection();		
		try {
			PreparedStatement ps = cn.prepareStatement("DELETE FROM Annonces WHERE id = ?");
			ps.setLong(1, Annonce.getId());
			ps.execute();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		
	}
	@Override
	public List<Annonce> getListOfAnnonces() {
		Connection cn = db.getConnection();
		ResultSet resultats = null;

		ArrayList Annonces = new ArrayList<Annonce>();
		try {
			PreparedStatement ps = cn.prepareStatement("SELECT * FROM AnnonceS");
			resultats = ps.executeQuery();
			    while (resultats.next()) {
			    	Annonce Annonce = new Annonce();
			    	Annonce.setId(resultats.getLong(1));
			    	Annonce.setTitle(resultats.getString(2));
			    	Annonce.setContent(resultats.getString(3));
			    	Annonce.setAuthor(userService.getUserById(resultats.getString(4)));
			    	Annonce.setCreationDate(resultats.getTimestamp(5));
			    	Annonce.setUpdateDate(resultats.getTimestamp(6));
			    	Annonce.setStatus(Status.fromOrdinal(resultats.getInt(7)));
			    	Annonces.add(Annonce);
			     }	   

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		return Annonces;
	}

}
