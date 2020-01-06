package ocsubtitles.manage;

import static org.junit.Assert.*;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import ocsubtitles.beans.SubtitleTranslateBean;
import ocsubtitles.beans.SubtitleTripletBean;
import ocsubtitles.manage.SubtitleTranslationManager;

public class TestSubtitleGatherManager {
private static final String FILE_NAME ="The Irishman_en.srt";
	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testGetSaveSubtitle() {
		int index = 0;
		SubtitleTranslationManager sgm = new SubtitleTranslationManager("STAR WARS");
		List<SubtitleTranslateBean> listTranslate = sgm.getSubtitles();
		SubtitleTripletBean triplet = new SubtitleTripletBean(index);
		LocalTime date1 = LocalTime.parse("23:36:17.838");
		LocalTime date2 = LocalTime.parse("23:36:17.839");
		triplet.setStart(date1);
		triplet.setEnd(date2);
		triplet.setText("Test text");
		SubtitleTranslateBean translate = new SubtitleTranslateBean(triplet, null);		
		assertEquals(listTranslate.get(index), translate);
	}
	
	@Test
	public void testUpdateSubtitleTranslation() {
		int index = 1;
		SubtitleTranslationManager sgm = new SubtitleTranslationManager(FILE_NAME);
		List<SubtitleTranslateBean> listTranslate = sgm.getSubtitles();
		Random rand = new Random();
		int num = rand.nextInt();
		listTranslate.get(index).setTranslation("New translation"+num);
		
		
		SubtitleTripletBean triplet = new SubtitleTripletBean(index+1);
		LocalTime date1 = LocalTime.parse("00:01:19.293");
		LocalTime date2 = LocalTime.parse("00:01:21.535");
		triplet.setStart(date1);
		triplet.setEnd(date2);
		triplet.setText("I've got one!");
		SubtitleTranslateBean translate = new SubtitleTranslateBean(triplet, "New translation"+num);		
		assertEquals(listTranslate.get(index), translate);
	}
	
	@Test
	public void testParseTranslation() {
		int index =0;
		Random rand = new Random();
		int num = rand.nextInt();
		String trans ="New translation"+num;
		
		SubtitleTranslationManager sgm = new SubtitleTranslationManager(FILE_NAME);
		Map<String, String[]> translations = new HashMap<>();
		String[] values = { trans };
		translations.put("translation1", values);
		
		sgm.parseTranslation(translations);
		List<SubtitleTranslateBean> listTranslate = sgm.getSubtitles();
		assertEquals(listTranslate.get(index).getTranslation(), trans);
	}
	
	@Test
	public void testUpdateTranslation() {
		int index =1;
		Random rand = new Random();
		int num = rand.nextInt();
		String trans ="New translation"+num;
		
		SubtitleTranslationManager sgm = new SubtitleTranslationManager(FILE_NAME);
		Map<String, String[]> translations = new HashMap<>();
		String[] values = { trans };
		translations.put("translation1", values);
		sgm.parseTranslation(translations);
		sgm.save();
		List<SubtitleTranslateBean> listTranslate = sgm.getSubtitles();
		assertEquals(listTranslate.get(index-1).getTranslation(), trans);
	}

}
