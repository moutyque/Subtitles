package com.ocsubtitles.manage;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestSubtitleExportManager {

	@Test
	public void test() {
		SubtitleExportManager srm = new SubtitleExportManager("STAR WARS");
		srm.generateFile();
	}

}
