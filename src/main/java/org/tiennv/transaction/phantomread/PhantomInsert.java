package org.tiennv.transaction.phantomread;

import java.sql.*;

public class PhantomInsert implements Runnable {

	private Connection conn;

	private static final String QUERY = "INSERT INTO test_transaction(name, balance, bank) VALUES(?,?,?)";

	public PhantomInsert(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void run() {
		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(QUERY);
			stmt.setString(1, "tiennv2");
			stmt.setInt(2, 16700);
			stmt.setString(3, "TCB");

			stmt.execute();
			conn.commit();
		} catch (SQLException e) {
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