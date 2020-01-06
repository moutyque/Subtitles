package ocsubtitles.manage;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ocsubtitles.beans.SubtitleTripletBean;

public class TestLineText {
	private LineTextManager line;
	private SubtitleTripletBean triplet;
	@Before
	public void setUp() throws Exception {
		triplet = new SubtitleTripletBean();
		line = new LineTextManager(triplet);
	}

	@Test
	public void testAddEmptyLine() {
		triplet.setText("disparait");
		line.add("");
		assertEquals("disparait", triplet.getText());
	}
	@Test
	public void testAddNotEmptyLine() {
		triplet.setText("disparait");
		line.add("-Test");
		assertEquals("disparait-Test", triplet.getText());
	}

}
