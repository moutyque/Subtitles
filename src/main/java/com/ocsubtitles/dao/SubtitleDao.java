package ocsubtitles.dao;
import java.util.List;

import ocsubtitles.beans.SubtitleFileBean;
import ocsubtitles.beans.SubtitleTranslateBean;
import ocsubtitles.dao.exceptions.DAOException;

public interface SubtitleDao {
SubtitleFileBean findMovie(String fileName)throws DAOException;
void save(SubtitleFileBean subtitleFile);
public void create(SubtitleTranslateBean sub, String string) throws DAOException;
SubtitleTranslateBean findEntry(long number, String fileName) throws DAOException;
public void update(SubtitleFileBean subtitleFile);
List<String> getAllMoviesTitle();

}
