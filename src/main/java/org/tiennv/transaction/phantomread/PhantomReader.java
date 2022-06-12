package org.tiennv.transaction.phantomread;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PhantomReader implements Runnable {

	private Connection conn;

	private static final String QUERY = "SELECT * FROM test_transaction";

	public PhantomReader(Connection conn) {
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
				System.out.println("Account Details- " + rs.getInt(1) + "-" + rs.getString(2) + "-" +
						rs.getString(3) + "-" + rs.getString(4));
			}

			Thread.currentThread().sleep(10000);
			System.out.println("AFTER WAKING UP");
			System.out.println("===============================================");

			rs = stmt.executeQuery();

			while (rs.next()) {
				System.out.println("Account Details- " + rs.getInt(1) + "-" + rs.getString(2) + "-" +
						rs.getString(3) + "-" + rs.getString(4));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}