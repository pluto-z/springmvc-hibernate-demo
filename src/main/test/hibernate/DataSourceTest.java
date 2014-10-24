package hibernate;

import com.ptsisi.daily.web.DataSourceConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by zhaoding on 14-10-24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DataSourceConfig.class)
public class DataSourceTest {

	@Autowired
	private DataSource dataSource;

	@Test
	public void test() throws Exception {
		System.out.println(1);
		Assert.assertNotNull(dataSource);
		Connection conn = dataSource.getConnection();
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery("select * from aaa");
		Assert.assertEquals(rs.getInt("id"),1);
		Assert.assertEquals(rs.getString("name"),"255");
	}
}
