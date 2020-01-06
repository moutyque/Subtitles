package ocsubtitles.manage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

import ocsubtitles.beans.SubtitleFileBean;
import ocsubtitles.dao.DAOFactory;
import ocsubtitles.dao.SubtitleDaoImpl;

public class SubtitleExportManager {
private SubtitleFileBean sfb;
private File file;
	public SubtitleExportManager(String fileName) {
		DAOFactory factory = DAOFactory.getInstance();
		SubtitleDaoImpl subImpl =  (SubtitleDaoImpl) factory.getSubtitleDao();
		sfb = subImpl.findMovie(fileName);
	}
	
	
	public File getFile() {
		return file;
	}


	public File generateFile() {
		
		file = null;
		OpenOption option = StandardOpenOption.APPEND;

		try {
			file = File.createTempFile(sfb.getName(), "");
			Files.write(file.toPath(), sfb.translationsToList(), option);
		} catch (IOException e) {
			System.err.println("Failed to create or writte in temp file");
			System.err.println(e.toString());
		}

return file;
	}
	
	public String readLineByLineJava8(File file) 
	{
	    StringBuilder contentBuilder = new StringBuilder();

	    try (Stream<String> stream = Files.lines( Paths.get(file.getAbsolutePath()), StandardCharsets.UTF_8)) 
	    {
	        stream.forEach(s -> contentBuilder.append(s).append("\n"));
	    }
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
	    return contentBuilder.toString();
	}


}
