package org.tiennv.algorithm;

import org.junit.Test;

public class Sha256Test {

	@Test
	public void testPreProcess() {

		Sha256 sha256 = new Sha256();
		StringBuilder output = sha256.preProcess("hello world");
		System.out.println(output);
		System.out.println(output.length());
	}
}
