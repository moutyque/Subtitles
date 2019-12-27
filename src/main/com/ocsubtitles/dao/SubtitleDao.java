package com.ocsubtitles.dao;
import java.sql.Connection;
import java.util.List;

import com.ocsubtitles.beans.SubtitleFileBean;
import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.dao.exceptions.DAOException;

public interface SubtitleDao {
List<SubtitleTripletBean> findMovie(String fileName)throws DAOException;
void save(SubtitleFileBean subtitleFile);
void create(SubtitleTripletBean triplet) throws DAOException;
SubtitleTripletBean findEntry(long number, String fileName) throws DAOException;
}
