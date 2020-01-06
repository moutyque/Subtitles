package ocsubtitles.servlets;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ocsubtitles.manage.SubtitleExportManager;

/**
 * Servlet implementation class downloadServlet
 */
@WebServlet( name="Download", urlPatterns = "/download" )

public class downloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public downloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filename = (String)request.getParameter("fileName");
		
	    if (filename == null)
	    {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parameter fileName missing");
	        return;
	    }
	    SubtitleExportManager sem = new SubtitleExportManager(filename);
		File file = sem.generateFile();
		
	    String result = sem.readLineByLineJava8(file);  // suppose result is your generated CSV contents

	    // prepare writing the result to the client as a "downloadable" file

	    response.setContentType("text/csv");
	    response.setHeader("Content-disposition", "attachment; filename=\""+filename+"\"");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setHeader("Expires", "-1");

	    // actually send result bytes
	    response.getOutputStream().write(result.getBytes());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
