package org.tss.map;

public interface Placeable {

	void place(Map map, double x, double y, double r);

	default void place(Map map, double x, double y) {
		place(map, x, y, 0);
	}

	public Map getMap();
}
