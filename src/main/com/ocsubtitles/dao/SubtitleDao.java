package com.ocsubtitles.dao;
import java.sql.Connection;
import java.util.List;

import com.ocsubtitles.beans.SubtitleFileBean;
import com.ocsubtitles.beans.SubtitleTranslateBean;
import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.dao.exceptions.DAOException;

public interface SubtitleDao {
SubtitleFileBean findMovie(String fileName)throws DAOException;
void save(SubtitleFileBean subtitleFile);
public void create(SubtitleTranslateBean sub, String string) throws DAOException;
SubtitleTranslateBean findEntry(long number, String fileName) throws DAOException;
public void update(SubtitleFileBean subtitleFile);

}
