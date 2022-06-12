package org.tiennv.transaction;

import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertTrue;

public class ConnectionPoolTest {

	@Test
	public void whenCalledgetConnection_thenCorrect() throws SQLException {
		ConnectionPool connectionPool = JDBCConnectionPool
				.create("jdbc:mysql://paave-dev.coo1pelwmlwz.ap-southeast-1.rds.amazonaws.com:3306/paave-virtual-core?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true",
						"admin", "QBEFrEWPDXHDQMxMQpnPT6YB7azaXBSg7pb3fgD");

		assertTrue(connectionPool.getConnection().isValid(1));
	}
}
