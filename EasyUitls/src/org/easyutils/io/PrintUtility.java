package org.easyutils.io;

public class PrintUtility {
	public static void print(String str, char chr, int... index) {
		System.out.print(str + chr + "\n");
	}

	public static void print(char chr, int index, String... str) {
		String sep = str[0];
		for (int i = 1; i < str.length; i++) {
			System.out.print(sep + str[i] + "\n");
		}
	}

	public static void print(String str, int index, char... chr) {
		System.out.print(index);
		for (char c : chr) {
			System.out.print(str + c);
		}
	}
}
