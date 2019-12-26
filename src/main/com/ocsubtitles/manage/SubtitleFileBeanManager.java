package com.ocsubtitles.manage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.ocsubtitles.beans.SubtitleFileBean;
import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.beans.exceptions.FileFormatException;
import com.ocsubtitles.dao.SubtitleDao;


public class SubtitleFileBeanManager {
	private final static Logger LOGGER = Logger.getLogger(SubtitleCreatorManager.class.getName());
	public static final int TAILLE_TAMPON = 10240;
	private SubtitleFileBean subtitleFile = new SubtitleFileBean();

	public SubtitleFileBeanManager(HttpServletRequest request, String path)
			throws IOException, FileFormatException, ServletException {
		Part part = request.getPart("fichier");
		String nomFichier = getNomFichier(part);

		// Si on a bien un fichier
		if (nomFichier != null && !nomFichier.isEmpty()) {
			if (!nomFichier.endsWith(SubtitleFileBean.SUBTITLE_EXTENSION))
				throw new FileFormatException("The file format is not .srt");
			// Corrige un bug du fonctionnement d'Internet Explorer
			nomFichier = nomFichier.substring(nomFichier.lastIndexOf('/') + 1)
					.substring(nomFichier.lastIndexOf('\\') + 1);

			path += File.separator + "tmp";
			// On écrit définitivement le fichier sur le disque
			writteFile(part, nomFichier, path);

			subtitleFile.setName(nomFichier);
			subtitleFile.setPath(path);
			ParseSubtitle parser = new ParseSubtitle();
			File tmp = new File(path, nomFichier);
			try {
				parser.parse(tmp);
				subtitleFile.setSubtitles(parser.getSubtitles());
				
			} catch (Exception e) {
				LOGGER.severe(e.toString());
			}
		} else {
			throw new IOException("No file name found");
		}
	}


	public void save(SubtitleDao subDAO) {
		subtitleFile.getSubtitles().forEach(item-> subDAO.create(item));
	}


	private void writteFile(Part part, String fileName, String path) throws IOException {
		path = this.formatPath(path);
		if (checkPath(path)) {
			try (BufferedInputStream input = new BufferedInputStream(part.getInputStream(), TAILLE_TAMPON);
					BufferedOutputStream output = new BufferedOutputStream(
							new FileOutputStream(new File(path + fileName)), TAILLE_TAMPON)) {
				byte[] tampon = new byte[TAILLE_TAMPON];
				int longueur;
				while ((longueur = input.read(tampon)) > 0) {
					output.write(tampon, 0, longueur);
				}

			} catch (Exception e) {
				throw new IOException("Failed to writte the input subtitle file");
			}
		} else {
			throw new IOException("The target folder to writte the file does not exist.");
		}
	}

	private String formatPath(String path) {
		if (!path.endsWith(File.separator)) {
			path += File.separator;
		}
		return path;
	}

	private boolean checkPath(String path) {
		boolean pathExist = false;

		File tmpDir = new File(path);
		if (!tmpDir.exists()) {
			if (tmpDir.mkdir()) {
				pathExist = true;
				System.out.println("Directory created successfully");
			} else {
				pathExist = false;
				System.out.println("Sorry couldn’t create specified directory");
			}
		} else {
			pathExist = true;
		}
		return pathExist;
	}

	private String getNomFichier(Part part) {
		for (String contentDisposition : part.getHeader("content-disposition").split(";")) {
			if (contentDisposition.trim().startsWith("filename")) {
				return contentDisposition.substring(contentDisposition.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}



}
