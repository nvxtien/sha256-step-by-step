package org.tiennv.algorithm;

import java.nio.charset.StandardCharsets;

public class Sha256 {

	public StringBuilder preProcess(String s) {
		byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes) {
			int val = b;
			for (int i = 0; i < 8; i++) {
				binary.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}
//			binary.append(' ');
		}

		int binaryOriginalSize = binary.length();

		binary.append("1");
//		System.out.println("'" + s + "' to binary: " + binary);

		int padSize = 512 - binary.length();

		for (int i = 0; i < padSize; i++) {
			binary.append("0");
		}

		return binary;
	}
}
