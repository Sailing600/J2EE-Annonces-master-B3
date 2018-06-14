package fr.epsi.myEpsi.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.epsi.myEpsi.beans.Annonce;
import fr.epsi.myEpsi.beans.Status;
import fr.epsi.myEpsi.beans.User;
import utils.CannotDeleteAdminException;
import utils.CannotDeleteMessageException;
import utils.DuplicateUserException;

public class AnnonceServiceTest {

	@Before
	public void init() {
		IAnnonceService annonceService = new AnnonceService();
		IUserService userService = new UserService();
		User user = new User("jmdoudou", "test", false);
		try {
			userService.addUser(user);
			Annonce annonce = new Annonce();
			annonce.setAuthor(user);
			annonce.setContent("test");
			annonce.setTitle("test");
			annonce.setStatus(Status.PRIVATE);
			annonceService.addAnnonces(annonce);
			Annonce annonce2 = new Annonce();
			annonce2 .setAuthor(user);
			annonce2 .setContent("test");
			annonce2 .setTitle("test");
			annonce2 .setStatus(Status.ARCHIVED);
			
		} catch (DuplicateUserException e) {
			fail("cannot create user");
		}
	}
	
	@Test
	public void YouCannotDeleteAnnonceOfOtherUser() {
		User userd = new User("jm", "test", false);
		IAnnonceService annonceService = new AnnonceService();
		IUserService userService = new UserService();
		User author = userService.getUserById("jm");
		List<Annonce> annonce = annonceService.getListOfAnnonces(author);
		try {
			annonceService.deleteAnnonce(annonce.get(0), userd);
			fail("You can delete Annonce of other user");	
		} catch (CannotDeleteMessageException e) {}
	
	}
	
	@Test
	public void YouCannotViewPrivateAndArchivedAnnonceOfOtherUser() {
		User userd = new User("jm", "test", false);
		IAnnonceService annonceService = new AnnonceService();
		List<Annonce> annonces = annonceService.getListOfAnnonces(userd);
		assertEquals(annonces.size(), 0);
	}
	
	@After
	public void after() {
		IAnnonceService annonceService = new AnnonceService();
		IUserService userService = new UserService();
		User user = userService.getUserById("jmdoudou");
		List<Annonce> list = annonceService.getListOfAnnonces(user);
		for(Annonce ann : list){
			try {
				annonceService.deleteAnnonce(ann, user);
			} catch (CannotDeleteMessageException e) {}
		}
		try {
			userService.deleteUser(user);
		} catch (CannotDeleteAdminException e) {
			e.printStackTrace();
		}
	}

}
