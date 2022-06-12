package org.tiennv.transaction.dirtyread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentRunImpl implements Runnable {

	private Connection conn;

	private static final String QUERY = "UPDATE test_transaction SET balance = 26000 WHERE id = 1";

	public PaymentRunImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void run() {
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(QUERY);
			stmt.execute();
			Thread.currentThread().sleep(3000);
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}