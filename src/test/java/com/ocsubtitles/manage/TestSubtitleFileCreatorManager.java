package ocsubtitles.manage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import ocsubtitles.beans.exceptions.FileFormatException;
import ocsubtitles.manage.SubtitleCreatorManager;


public class TestSubtitleFileCreatorManager extends Mockito {
	private HttpServletRequest request;
	private File file;
	private Part part;
	private static final String HEADER_CONTENT ="content-disposition";
	//private static String PATH_RESOURCES ="";

	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();

	@Before
	public void setUp() throws IOException, ServletException {
//		PATH_RESOURCES = this.getClass().getPackage().getName().replace(".", "/") + "/resources/";
		ClassLoader classLoader = getClass().getClassLoader();
		URL resourcesPath = classLoader.getResource("testSmall.srt");
		String path = resourcesPath.getPath();
		path = URLDecoder.decode(path, "UTF-8");
		file = new File(path);
//		ClassLoader.getSystemResource("testSmall.srt")
		InputStream targetStream = ClassLoader.getSystemResourceAsStream("testSmall.srt");
//        FileUtils.copyInputStreamToFile(targetStream, file);

		// HttpServletRequest request, String path
		request = mock(HttpServletRequest.class);
		part = mock(Part.class);
		when(request.getPart("fichier")).thenReturn(part);
		when(part.getInputStream()).thenReturn(targetStream);
		
	}

	@Test
	public void testCreation() {
		when(part.getHeader(HEADER_CONTENT))
				.thenReturn("form-data; name=\"fichier\"; filename=\"The Irishman_en.srt\"");

		try {
			SubtitleCreatorManager subtitle = new SubtitleCreatorManager(request, file.getParentFile().toString());
			
		} catch (IOException | FileFormatException | ServletException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testWrongFileName() throws IOException, FileFormatException, ServletException {
		when(part.getHeader(HEADER_CONTENT))
				.thenReturn("form-data; name=\"fichier\"; filename=\"The Irishman_en.test\"");
		exceptionRule.expect(FileFormatException.class);
		exceptionRule.expectMessage("The file format is not .srt");
		SubtitleCreatorManager subtitle = new SubtitleCreatorManager(request, file.getParentFile().toString());

	}

	@Test
	public void testWrongPath() throws IOException, FileFormatException, ServletException {
		when(part.getHeader(HEADER_CONTENT))
				.thenReturn("form-data; name=\"fichier\"; filename=\"The Irishman_en.srt\"");
		Double rand = Math.random();
		exceptionRule.expect(IOException.class);
		exceptionRule.expectMessage("The target folder to writte the file does not exist.");
		SubtitleCreatorManager subtitle = new SubtitleCreatorManager(request,
				file.getParentFile().toString() + "\\" + rand);

	}

	@Test
	public void testNoFileName() throws IOException, FileFormatException, ServletException {
		when(part.getHeader(HEADER_CONTENT)).thenReturn("form-data; name=\"fichier\";");
		Double rand = Math.random();
		exceptionRule.expect(IOException.class);
		exceptionRule.expectMessage("No file name found");
		SubtitleCreatorManager subtitle = new SubtitleCreatorManager(request,
				file.getParentFile().toString() + "\\" + rand);

	}



}
