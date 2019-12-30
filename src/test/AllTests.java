import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({com.ocsubtitles.manage.AllTestsManage.class, com.ocsubtitles.dao.AllTestsDAO.class,com.ocsubtitles.beans.AllTestsBean.class})
public class AllTests {

}
