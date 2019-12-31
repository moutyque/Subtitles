package com.ocsubtitles.beans;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.ocsubtitles.manage.TestSubtitleFileCreatorManager;

@RunWith(Suite.class)
@SuiteClasses({ TestSubtitleFileBean.class,TestTriplet.class,TestTestSubtitleTranslate.class })
public class AllTestsBean {

}
