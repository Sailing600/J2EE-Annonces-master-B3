package fr.epsi.myEpsi.service;

import java.util.List;

import fr.epsi.myEpsi.beans.Annonce;
import fr.epsi.myEpsi.beans.User;
import utils.CannotDeleteMessageException;

public interface IAnnonceService {

	List<Annonce> getListOfAnnonces(User user);
	List<Annonce> getListOfAnnonces();
	Annonce getAnnonces(Long id);
	void addAnnonces(Annonce annonces);
	void updateAnnoncesStatus(Annonce message, int status);
	void deleteAnnonce(Annonce annonce, User connected) throws CannotDeleteMessageException;

}
