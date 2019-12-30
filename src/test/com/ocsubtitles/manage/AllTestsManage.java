package com.ocsubtitles.manage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestLineNumberManager.class, TestLineTime.class, TestParseSubtitle.class,
		TestSubtitleFileCreatorManager.class, TestSubtitleGatherManager.class, TestLineText.class })
public class AllTestsManage {

}
