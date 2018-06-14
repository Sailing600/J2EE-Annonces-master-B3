package fr.epsi.myEpsi.jmx;

import java.security.Timestamp;
import java.util.Date;

import fr.epsi.myEpsi.beans.Annonce;
import fr.epsi.myEpsi.beans.Status;
import fr.epsi.myEpsi.service.IAnnonceService;
import fr.epsi.myEpsi.service.AnnonceService;
import fr.epsi.myEpsi.service.UserService;

public class Premier implements PremierMBean {

	private static String name = "Premier MBEAN";
			private int valeur = 1000;
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getValue() {
		return valeur;
	}

	@Override
	public void setValue(int valeur) {
		this.valeur = valeur;
		
	}

	@Override
	public void refresh() {
		System.out.println("Refresh");
		
	}
	@Override
	public Integer getNumberFfAnnonces()
	{
		IAnnonceService AnnoncesService = new AnnonceService();
    	Integer AnnoncesSize = AnnoncesService.getListOfAnnonces().size();
    	return AnnoncesSize;
	}
	@Override 
	public void pushAdminAnnonces(String AnnoncesContenu)
	{
		AnnonceService AnnoncesService = new AnnonceService();
		UserService userService = new UserService();
		Annonce monAnnonces = new Annonce();
		monAnnonces.setAuthor(userService.getUserById("ADMIN"));
		monAnnonces.setStatus(Status.PUBLIC);
		monAnnonces.setCreationDate(new java.sql.Timestamp(new Date().getTime()));
		monAnnonces.setTitle("Admin Broadcast");
		monAnnonces.setContent(AnnoncesContenu);
		AnnoncesService.addAnnonces(monAnnonces);
	
	}

}
