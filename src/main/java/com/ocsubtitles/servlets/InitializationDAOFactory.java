package ocsubtitles.servlets;

import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import ocsubtitles.dao.DAOFactory;

import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
@WebListener
public class InitializationDAOFactory implements ServletContextListener {
	private static final String ATT_DAO_FACTORY = "daofactory";
	   static Logger logger = Logger.getLogger(InitializationDAOFactory.class.getName());

    private DAOFactory          daoFactory = null;

    @Override
    public void contextInitialized( ServletContextEvent event ) {
    	logger.info("Initialize Context with DAOFactory Loading start");
        /* Récupération du ServletContext lors du chargement de l'application */
        ServletContext servletContext = event.getServletContext();
        /* Instanciation de notre DAOFactory */
        this.daoFactory = DAOFactory.getInstance();
        /* Enregistrement dans un attribut ayant pour portée toute l'application */
        servletContext.setAttribute( ATT_DAO_FACTORY, this.daoFactory );
        logger.info("Initialize Context with DAOFactory Loading end");
    }

    @Override
    public void contextDestroyed( ServletContextEvent event ) {
        /* Rien à réaliser lors de la fermeture de l'application... */
    }
}
