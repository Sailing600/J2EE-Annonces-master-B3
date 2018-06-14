package fr.epsi.myEpsi.service;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import fr.epsi.myEpsi.beans.Annonce;
import fr.epsi.myEpsi.beans.User;
import fr.epsi.myEpsi.dao.IAnnonceDao;
import fr.epsi.myEpsi.dao.AnnonceDao;
import utils.CannotDeleteMessageException;

public class AnnonceService implements IAnnonceService {

	IAnnonceDao annonceDao;
	
	
	public AnnonceService(){
		annonceDao = new AnnonceDao();
	}
	@Override
	public List<Annonce> getListOfAnnonces(User user) {
		return annonceDao.getListOfAnnonces(user);
	}
	

	@Override
	public Annonce getAnnonces(Long id) {
		return annonceDao.getAnnonces(id);
	}

	@Override
	public void addAnnonces(Annonce Annonces) {
		annonceDao.addAnnonces(Annonces);
		
	}

	@Override
	public void updateAnnoncesStatus(Annonce Annonces, int status) {
		Annonces.setUpdateDate(new Timestamp(new Date().getTime()));
		annonceDao.updateAnnoncesStatus(Annonces, status);
	}

	@Override
	public void deleteAnnonce(Annonce Annonces, User connected) throws CannotDeleteMessageException {
		if(Annonces.getAuthor().getId().equals(connected.getId()) || connected.getAdministrator()){
			annonceDao.deleteAnnonces(Annonces);
		} else {
			throw new CannotDeleteMessageException();
		}
	}
	@Override
	public List<Annonce> getListOfAnnonces() {
		return annonceDao.getListOfAnnonces();
	}


}
