package org.tiennv.transaction.phantomread;

import org.tiennv.transaction.ConnectionPool;
import org.tiennv.transaction.JDBCConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class PhantomReadExample {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws SQLException {
		ConnectionPool pool = JDBCConnectionPool
				.create("jdbc:mysql://paave-dev.coo1pelwmlwz.ap-southeast-1.rds.amazonaws.com:3306/paave-virtual-core?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true",
						"admin", "QBEFrEWPDXHDQMxMQpnPT6YB7azaXBSg7pb3fgD");

		Connection connInsert = pool.getConnection();
		Connection connReader = pool.getConnection();
		try {
			connInsert.setAutoCommit(false);
			connInsert.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			connReader.setAutoCommit(false);
//			connInsert.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			connReader.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Thread readThread = new Thread(new PhantomReader(connReader));
		Thread insertThread = new Thread(new PhantomInsert(connInsert));
		readThread.start();
		insertThread.start();

//  pool.returnConnection(connReader);
//  pool.returnConnection(connInsert);

	}
}