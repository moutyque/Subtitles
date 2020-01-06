package ocsubtitles.manage;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class TestSubtitleExportManager {
	@Rule
    public TemporaryFolder folder= new TemporaryFolder();
	@Test
	public void test() throws IOException {
		SubtitleExportManager srm = new SubtitleExportManager("STAR WARS");
		File createdFile= folder.newFile("myfile.txt");
		String actualResult = srm.readLineByLineJava8(srm.generateFile());
		StringBuilder sb = new StringBuilder();
		String expectedResult= "";
		sb.append("0").append("\n");
		sb.append("23:36:17.838").append("-->").append("23:36:17.839").append("\n");
		sb.append(" ").append("\n").append("\n");
		String expectedString = sb.toString();
		
		assertEquals(expectedString,actualResult);
		
	}

}
