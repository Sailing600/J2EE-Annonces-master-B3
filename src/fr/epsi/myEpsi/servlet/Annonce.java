package fr.epsi.myEpsi.servlet;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.epsi.myEpsi.beans.Status;
import fr.epsi.myEpsi.beans.User;
import fr.epsi.myEpsi.service.IAnnonceService;
import fr.epsi.myEpsi.service.AnnonceService;
import utils.CannotDeleteMessageException;

/**
 * Servlet implementation class Annonce
 */
@WebServlet("/Annonce")
public class Annonce extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IAnnonceService AnnonceService;
	Logger logger =  LogManager.getLogger(Annonce.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Annonce() {
        super();
        AnnonceService = new AnnonceService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String test = request.getParameter("id");

		User connected = (User) request.getSession().getAttribute("user");	
		ArrayList<Status> listStatus = Status.getList();
	
		if(test != null && connected != null){
			logger.info("GET /Annonce");
			logger.debug(request);
			fr.epsi.myEpsi.beans.Annonce Annonce = AnnonceService.getAnnonces(Long.parseLong(request.getParameter("id"))); 
			request.setAttribute("Annonce", Annonce );
			request.setAttribute("status", listStatus );
			request.getRequestDispatcher("Annonce.jsp").forward(request, response);
		} else {
			 response.sendRedirect("Annonces");
		}
	}

	/** 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("POST /Annonce");
		logger.debug(request);
		User connected = (User) request.getSession().getAttribute("user");
		if(connected == null){
				logger.error("User not connectd",request);
			  response.sendRedirect("Signin");
		} else if(request.getParameter("action") != null && request.getParameter("action").equals("DELETE")) {
			this.doDelete(request, response);
		} else if(request.getParameter("action") != null && request.getParameter("action").equals("PUT")) {
			this.doPut(request, response);
		}else {   
			fr.epsi.myEpsi.beans.Annonce Annonce = new fr.epsi.myEpsi.beans.Annonce();
			Annonce.setTitle(request.getParameter("title"));
		    Annonce.setContent(request.getParameter("content"));
		    Annonce.setCreationDate(new Timestamp(new Date().getTime()));
		    Annonce.setUpdateDate(new Timestamp(new Date().getTime()));
		    Annonce.setAuthor(connected); 
		    Annonce.setStatus(Status.valueOf(request.getParameter("status")));
		    AnnonceService.addAnnonces(Annonce);
		    response.sendRedirect("Annonces");
		}
	 }
	
	/** 
	 * @see HttpServlet#doDelete(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User connected = (User) request.getSession().getAttribute("user");
		String id = request.getParameter("id");
		if(id != null && connected != null){
			fr.epsi.myEpsi.beans.Annonce Annonce = AnnonceService.getAnnonces(Long.parseLong(request.getParameter("id"))); 
			try {
				AnnonceService.deleteAnnonce(Annonce, connected);
			} catch (CannotDeleteMessageException e) {
				logger.error("You are not authorized to delete this post");
			}
			response.sendRedirect("Annonces");
		} else {
			 response.sendRedirect("Signin");
		}
	}
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		User connected = (User) request.getSession().getAttribute("user");
		if(connected == null){
			  response.sendRedirect("Signin");
		} else {
			fr.epsi.myEpsi.beans.Annonce Annonce = AnnonceService.getAnnonces(Long.parseLong(request.getParameter("id")));
		    AnnonceService.updateAnnoncesStatus(Annonce, Status.valueOf(request.getParameter("status")).ordinal());
		    response.sendRedirect("Annonces");
		}
	}
}
