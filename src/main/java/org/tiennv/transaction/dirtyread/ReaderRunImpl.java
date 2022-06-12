package org.tiennv.transaction.dirtyread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReaderRunImpl implements Runnable {

	private Connection conn;

	private static final String QUERY = "SELECT balance FROM test_transaction WHERE id = 1";

	public ReaderRunImpl(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void run() {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(QUERY);
			rs = stmt.executeQuery();
			while (rs.next()) {
				System.out.println("Balance is:" + rs.getDouble(1));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
