package ocsubtitles.servlets;



import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ocsubtitles.dao.DAOFactory;
import ocsubtitles.dao.SubtitleDao;
import ocsubtitles.manage.SubtitleCreatorManager;


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
		List<String> movies = subDAO.getAllMoviesTitle();
		request.setAttribute("movies",movies);
		this.getServletContext().getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//Depending of the button pressed the related function is call	
		if(request.getParameter("submit").contentEquals("Create")) {
			createSubtitle(request, response);
		}
		else if(request.getParameter("submit").contentEquals("Export")) {
			export(request, response);
		}
		else if(request.getParameter("submit").contentEquals("Translate")) {
			translate(request, response);
		}
		
		

	}
	
	private void export(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		String fileName = request.getParameter("movieList");
		
		
		try {			
			response.sendRedirect("download?fileName="+fileName);
			
		} catch (Exception e) {
			request.setAttribute("message", e.toString());
			doGet(request,response);
			log("Error occured");
		}
		
		}  
	
	private void translate(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		String fileName = request.getParameter("movieList");
		
		
		try {			
			response.sendRedirect("translation?fileName="+fileName);
			
		} catch (Exception e) {
			request.setAttribute("message", e.toString());
			doGet(request,response);
			log("Error occured");
		}
		
		} 
	private void createSubtitle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			SubtitleCreatorManager subtitle = new SubtitleCreatorManager(request,
					this.getServletContext().getRealPath("/WEB-INF"));
			String fileName = subtitle.getSubtitleFile().getName();			
			
			response.sendRedirect("translation?fileName="+fileName);
			
		} catch (Exception e) {
			request.setAttribute("message", e.toString());
			doGet(request,response);
			log("Error occured");
		}
	}

}
