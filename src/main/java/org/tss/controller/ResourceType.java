package org.tss.controller;

public enum ResourceType {
	CREDIT;

	public static String convert(double value) {
		if (value >= 1E3) {
			return (int) (value / 1E3) + "k";
		} else {
			System.out.println(value + " < " + 1E3);
		}
		return (int) value + "";
	}
}
