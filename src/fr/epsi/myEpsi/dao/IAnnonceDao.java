package fr.epsi.myEpsi.dao;

import java.util.List;

import fr.epsi.myEpsi.beans.Annonce;
import fr.epsi.myEpsi.beans.User;

public interface IAnnonceDao {

	List<Annonce> getListOfAnnonces(User user);
	List<Annonce> getListOfAnnonces();
	Annonce getAnnonces(Long id);
	void addAnnonces(Annonce Annonces);
	void updateAnnoncesStatus(Annonce Annonces, int status);
	void deleteAnnonces(Annonce Annonces);

}
