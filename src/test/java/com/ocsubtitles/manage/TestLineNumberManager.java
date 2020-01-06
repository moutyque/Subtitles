package ocsubtitles.manage;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ocsubtitles.beans.SubtitleTripletBean;

public class TestLineNumberManager {
private LineNumberManager line;
private SubtitleTripletBean triplet;
	@Before
	public void setUp() throws Exception {
		triplet = new SubtitleTripletBean();
		triplet.setText("Text");
		line = new LineNumberManager(triplet);
	}


	@Test
	public void testAdd() {
		line.add("1");
		assertEquals(line.getTriplet().getNumber(), 1);
		
	}

	@Test
	public void testGetTriplet() {
		SubtitleTripletBean triplTmp = line.getTriplet();
		assertEquals(triplet, triplTmp);
	}

}
