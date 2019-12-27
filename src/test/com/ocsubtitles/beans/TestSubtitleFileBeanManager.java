package com.ocsubtitles.beans;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import com.ocsubtitles.beans.exceptions.FileFormatException;
import com.ocsubtitles.dao.DAOFactory;
import com.ocsubtitles.dao.SubtitleDaoImpl;

import com.ocsubtitles.manage.SubtitleFileBeanManager;

public class TestSubtitleFileBeanManager extends Mockito {
	private HttpServletRequest request;
	private File file;
	private Part part;
	private final String headerContent ="content-disposition";
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Before
	public void setUp() throws IOException, ServletException {
		ClassLoader classLoader = getClass().getClassLoader();
		URL resourcesPath = classLoader.getResource("testSmall.srt");
		String path = resourcesPath.getPath();
		path = URLDecoder.decode(path, "UTF-8");
		file = new File(path);
		InputStream targetStream = new FileInputStream(file);

		// HttpServletRequest request, String path
		request = mock(HttpServletRequest.class);
		part = mock(Part.class);
		when(request.getPart("fichier")).thenReturn(part);
		when(part.getInputStream()).thenReturn(targetStream);

	}

	@Test
	public void testCreation() {
		when(part.getHeader(headerContent))
				.thenReturn("form-data; name=\"fichier\"; filename=\"The Irishman_en.srt\"");

		try {
			SubtitleFileBeanManager subtitle = new SubtitleFileBeanManager(request, file.getParentFile().toString());
			assertTrue(true);
		} catch (IOException | FileFormatException | ServletException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testWrongFileName() throws IOException, FileFormatException, ServletException {
		when(part.getHeader(headerContent))
				.thenReturn("form-data; name=\"fichier\"; filename=\"The Irishman_en.test\"");
		exceptionRule.expect(FileFormatException.class);
		exceptionRule.expectMessage("The file format is not .srt");
		SubtitleFileBeanManager subtitle = new SubtitleFileBeanManager(request, file.getParentFile().toString());

	}

	@Test
	public void testWrongPath() throws IOException, FileFormatException, ServletException {
		when(part.getHeader(headerContent))
				.thenReturn("form-data; name=\"fichier\"; filename=\"The Irishman_en.srt\"");
		Double rand = Math.random();
		exceptionRule.expect(IOException.class);
		exceptionRule.expectMessage("The target folder to writte the file does not exist.");
		SubtitleFileBeanManager subtitle = new SubtitleFileBeanManager(request,
				file.getParentFile().toString() + "\\" + rand);

	}

	@Test
	public void testNoFileName() throws IOException, FileFormatException, ServletException {
		when(part.getHeader(headerContent)).thenReturn("form-data; name=\"fichier\";");
		Double rand = Math.random();
		exceptionRule.expect(IOException.class);
		exceptionRule.expectMessage("No file name found");
		SubtitleFileBeanManager subtitle = new SubtitleFileBeanManager(request,
				file.getParentFile().toString() + "\\" + rand);

	}

	@Test
	public void testSave() throws IOException, FileFormatException, ServletException {
		when(part.getHeader(headerContent))
		.thenReturn("form-data; name=\"fichier\"; filename=\"testSmall.srt\"");
		SubtitleFileBean subtitle = new SubtitleFileBean();
		
		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDaoImpl subDAO = (SubtitleDaoImpl) factory.getSubtitleDao();
		subDAO.save(subtitle);

	}


}
