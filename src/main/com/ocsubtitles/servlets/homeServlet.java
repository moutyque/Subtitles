package com.ocsubtitles.servlets;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.ocsubtitles.dao.SubtitleDao;
import com.ocsubtitles.manage.SubtitleCreatorManager;
import com.ocsubtitles.dao.DAOFactory;


/**
 * Servlet implementation class homeServlet
 */
@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024
		* 1024 * 5 * 5)
@WebServlet( name="Home", urlPatterns = "/Home" )

public class homeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  private SubtitleDao     subDAO;
	  public static final String CONF_DAO_FACTORY = "daofactory";
	  
	    public void init() throws ServletException {
	        /* Récupération d'une instance de notre DAO Utilisateur */
	        this.subDAO = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY )).getSubtitleDao();
	    }
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public homeServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// On récupère le champ du fichier
		try {
			SubtitleCreatorManager subtitle = new SubtitleCreatorManager(request,
					this.getServletContext().getRealPath("/WEB-INF"));
			request.setAttribute("message", "File successfully imported");
			//TODO display new JSP
		} catch (Exception e) {
			request.setAttribute("message", e.toString());
			log("Error occured");
		} finally {
			this.doGet(request, response);
		}

	}

}
