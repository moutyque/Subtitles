package com.ocsubtitles.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestDAOFactory.class, TestSubtitleDaoImpl.class,TestDAOUtil.class })
public class AllTestsDAO {

}
