package org.tss.base;

import org.tss.map.Map;

public interface Placeable {

	void place(Map map, double x, double y);

	void remove();
}
