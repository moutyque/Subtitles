package com.ocsubtitles.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocsubtitles.beans.SubtitleTranslateBean;
import com.ocsubtitles.dao.DAOFactory;
import com.ocsubtitles.dao.SubtitleDao;
import com.ocsubtitles.manage.SubtitleTranslationManager;

/**
 * Servlet implementation class updateServlet
 */
@WebServlet("/translation")
public class updateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SubtitleDao     subDAO;
	  public static final String CONF_DAO_FACTORY = "daofactory";
	  private SubtitleTranslationManager subGather;
	  
	    public void init() throws ServletException {
	        /* Récupération d'une instance de notre DAO Utilisateur */
	        this.subDAO = ((DAOFactory) getServletContext().getAttribute(CONF_DAO_FACTORY )).getSubtitleDao();
	    }
	/**
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		if(fileName != null && !fileName.isEmpty()) {
			subGather = new SubtitleTranslationManager(fileName);
			request.setAttribute("translatedSub", subGather.getSubtitles());
		}
		request.getRequestDispatcher("/WEB-INF/translation.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String, String[]> translations = request.getParameterMap();
		subGather.parseTranslation(translations);
		subGather.save();
		doGet(request, response);
	}

}
