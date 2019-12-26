package com.ocsubtitles.dao;
import java.util.List;

import com.ocsubtitles.beans.SubtitleTripletBean;
import com.ocsubtitles.dao.exceptions.DAOException;

public interface SubtitleDao {
void create (SubtitleTripletBean triplet) throws DAOException;
public SubtitleTripletBean findEntry(long number, String fileName) throws DAOException;
List<SubtitleTripletBean> findMovie(String fileName)throws DAOException;
}
