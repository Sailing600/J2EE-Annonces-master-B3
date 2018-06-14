package fr.epsi.myEpsi.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.epsi.myEpsi.beans.Annonce;
import fr.epsi.myEpsi.beans.User;
import fr.epsi.myEpsi.beans.Status;
import fr.epsi.myEpsi.service.IAnnonceService;
import fr.epsi.myEpsi.service.AnnonceService;
import fr.epsi.myEpsi.service.AnnonceService;

/**
 * Servlet implementation class Annoncess
 */
@WebServlet("/Annonces")
public class Annonces extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;
    private IAnnonceService AnnoncesService;
    Logger logger =  LogManager.getLogger(Annonces.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Annonces() {
        super();
        AnnoncesService = new AnnonceService();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("GET /Annoncess");
	    logger.debug(request);
		User connected = (User) request.getSession().getAttribute("user");
		if(connected == null){
			logger.error("user not connected", request);
			  response.sendRedirect("Signin");
		} else {
			// Create List Of Status
			ArrayList<Status> listStatus;
			if(connected.getAdministrator()){
				listStatus = new ArrayList<Status>();
				listStatus.add(Status.PUBLIC);
			} else {
				listStatus = Status.getList();
			}
			
			// Get list Of Annoncess
			List<Annonce> Annonces = AnnoncesService.getListOfAnnonces(connected);
			request.setAttribute("user", connected);
			request.setAttribute("Annoncess", Annonces);
			
			// Add Status for option form
			request.setAttribute("status", listStatus );
			request.getRequestDispatcher("Annoncess.jsp").forward(request, response);
		}
	}
}
