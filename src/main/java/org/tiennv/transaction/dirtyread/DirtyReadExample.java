package org.tiennv.transaction.dirtyread;

import org.tiennv.transaction.ConnectionPool;
import org.tiennv.transaction.JDBCConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

public class DirtyReadExample {

	/**
	 * @param args
	 * @throws SQLException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws SQLException {
		ConnectionPool pool = JDBCConnectionPool
                .create("jdbc:mysql://paave-dev.coo1pelwmlwz.ap-southeast-1.rds.amazonaws.com:3306/paave-virtual-core?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC&createDatabaseIfNotExist=true",
                        "admin", "QBEFrEWPDXHDQMxMQpnPT6YB7azaXBSg7pb3fgD");

		Connection connPymt = pool.getConnection();
		Connection connReader = pool.getConnection();
		try {
			connPymt.setAutoCommit(false);
			connPymt.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

			connReader.setAutoCommit(false);
			connReader.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
//			connReader.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		} catch (SQLException e) {
			e.printStackTrace();
		}


		Thread pymtThread = new Thread(new PaymentRunImpl(connPymt));
		Thread readerThread = new Thread(new ReaderRunImpl(connReader));

		pymtThread.start();
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		readerThread.start();

//		pool.returnConnection(connPymt);
//		pool.returnConnection(connReader);
	}

}